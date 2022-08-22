package com.mapofzones.endpointchecker.services.node.rpc;

import com.mapofzones.endpointchecker.domain.NodeRpcAddress;
import com.mapofzones.endpointchecker.services.node.rpc.client.JsonRpcClient;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.DefaultNodeInfo;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.DefaultNodeInfoOther;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.NetInfo;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.Peer;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.Status;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.SyncInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RpcServiceTest {

    private final JsonRpcClient jsonRpcClient = Mockito.mock(JsonRpcClient.class);
    private final RpcService rpcService = new RpcService(jsonRpcClient);

    @Test
    void checkLivelinessAndFindPeers_MalformedURLException_Test() throws MalformedURLException {
        lenient().doThrow(new MalformedURLException()).when(jsonRpcClient).findNodeInfo(any());
        assertEquals(0, rpcService.checkLivelinessAndFindPeers(new NodeRpcAddress(), new HashSet<>()).size());
    }

    @Test
    void checkLivelinessAndFindPeers_Exception_Test() throws MalformedURLException {
        NodeRpcAddress rpcAddress = new NodeRpcAddress();
        rpcAddress.setIsAlive(true);
        lenient().doReturn(null).when(jsonRpcClient).findNodeInfo(any());

        Map<NodeAddressDto, Set<NodeRpcAddress>> map = rpcService.checkLivelinessAndFindPeers(rpcAddress, new HashSet<>());
        assertAll(() -> {
            assertEquals(0, map.size());
            assertFalse(rpcAddress.getIsAlive());
        });

    }

    @Test
    void checkLiveliness_ContainsInZoneSet_Test() {
        NodeRpcAddress nodeRpcAddress = new NodeRpcAddress();
        nodeRpcAddress.setZone("network-2");

        Status status = getStatus();
        Set<String> zones = new HashSet<>(Arrays.asList("network-1", "network-2", "network-3"));
        ReflectionTestUtils.invokeMethod(rpcService, "checkLiveliness", nodeRpcAddress, zones, status);
        assertAll(() -> {
            assertEquals(true, nodeRpcAddress.getIsAlive());
            assertEquals("network-1", nodeRpcAddress.getZone());
        });
    }

    @Test
    void checkLiveliness_NotContainsInZoneSet_Test() {
        NodeRpcAddress nodeRpcAddress = new NodeRpcAddress();
        nodeRpcAddress.setZone("network-2");

        Status status = getStatus();
        Set<String> zones = new HashSet<>(Arrays.asList("network-5", "network-6"));
        ReflectionTestUtils.invokeMethod(rpcService, "checkLiveliness", nodeRpcAddress, zones, status);
        assertAll(() -> {
            assertEquals(false, nodeRpcAddress.getIsAlive());
            assertEquals("network-2", nodeRpcAddress.getZone());
        });
    }

    @Test
    void checkLiveliness_WithException_Test() {
        Status status = mock(Status.class);
        when(status.getNodeInfo()).thenThrow(new NullPointerException("Null"));

        NodeRpcAddress nodeRpcAddress = new NodeRpcAddress();

        ReflectionTestUtils.invokeMethod(rpcService, "checkLiveliness", nodeRpcAddress, new HashSet<>(), status);
        assertEquals(false, nodeRpcAddress.getIsAlive());
    }


    @Test
    void findPeersTest() {

        DefaultNodeInfoOther other = new DefaultNodeInfoOther();
        ReflectionTestUtils.setField(other, "rpcAddress", "tcp://127.0.0.1:26647");

        DefaultNodeInfo defaultNodeInfo = new DefaultNodeInfo();
        ReflectionTestUtils.setField(defaultNodeInfo, "other", other);

        Peer peer1 = new Peer();
        ReflectionTestUtils.setField(peer1, "remoteIP", "5.5.5.5");
        ReflectionTestUtils.setField(peer1, "nodeInfo", defaultNodeInfo);

        Peer peer2 = new Peer();
        ReflectionTestUtils.setField(peer2, "remoteIP", "6.6.6.6,0.0.0.0");
        ReflectionTestUtils.setField(peer2, "nodeInfo", defaultNodeInfo);

        List<Peer> peers = new ArrayList<>();
        peers.add(peer1);
        peers.add(peer2);

        NetInfo netInfo = new NetInfo();
        ReflectionTestUtils.setField(netInfo, "peers", peers);

        Map<NodeAddressDto, Set<NodeRpcAddress>> foundPeers =
                ReflectionTestUtils.invokeMethod(rpcService, "findPeers", "network-1", netInfo);

        assertEquals(2, foundPeers.size());
    }

    @Test
    void buildNodeRpcAddress() {
        Peer peer = new Peer();
        ReflectionTestUtils.setField(peer, "remoteIP", "5.5.5.5");

        Map.Entry<NodeAddressDto, NodeRpcAddress> builtNodeRpcAddress =
                ReflectionTestUtils.invokeMethod(rpcService, "buildNodeRpcAddress", "network-1", "5555", peer);

        assertAll(() -> {
            assertEquals("5.5.5.5", builtNodeRpcAddress.getKey().getIpOrDns());
            assertEquals("network-1", builtNodeRpcAddress.getValue().getZone());
            assertEquals("http://5.5.5.5:5555", builtNodeRpcAddress.getValue().getRpcAddress());
            assertFalse(builtNodeRpcAddress.getValue().getIsAlive());
        });
    }

    @Test
    void buildExtraNodeRpcAddress() {
        NodeRpcAddress sourceNodeRpcAddress = new NodeRpcAddress();
        sourceNodeRpcAddress.setZone("network-1");
        sourceNodeRpcAddress.setRpcAddress("http://5.5.5.5:1111");
        sourceNodeRpcAddress.setNodeAddress("5.5.5.5");

        NodeRpcAddress extraNodeRpcAddress = ReflectionTestUtils.invokeMethod(rpcService, "buildExtraNodeRpcAddress", sourceNodeRpcAddress);
        assertAll(() -> {
            assertEquals("network-1", extraNodeRpcAddress.getZone());
            assertEquals("5.5.5.5", extraNodeRpcAddress.getNodeAddress());
            assertEquals("http://5.5.5.5:26657", extraNodeRpcAddress.getRpcAddress());
        });
    }

    @Test
    void findPort() {
        String result = ReflectionTestUtils.invokeMethod(rpcService, "findPort", "5.5.5.5:1111");
        assertEquals("1111", result);
    }

    @Test
    void findRpcAddress_Test() {
        String result = ReflectionTestUtils.invokeMethod(rpcService, "findRpcAddress", "5.5.5.5");
        assertEquals("5.5.5.5", result);
    }

    @Test
    void findRpcAddress_WithIgnoreAddress_Test() {
        String result = ReflectionTestUtils.invokeMethod(rpcService, "findRpcAddress", "5.5.5.5,0.0.0.0,127.0.0.1");
        assertEquals("5.5.5.5", result);
    }

    @Test
    void isMultiRpcAddress_True_Test() {
        boolean result = Boolean.TRUE.equals(ReflectionTestUtils.invokeMethod(rpcService, "isMultiRpcAddress", "0.0.0.0,1.1.1.1"));
        assertTrue(result);
    }

    @Test
    void isMultiRpcAddress_False_Test() {
        boolean result = Boolean.FALSE.equals(ReflectionTestUtils.invokeMethod(rpcService, "isMultiRpcAddress", "0.0.0.0"));
        assertTrue(result);
    }

    Status getStatus() {
        SyncInfo syncInfo = new SyncInfo();
        ReflectionTestUtils.setField(syncInfo, "earliestBlockHeight", 1L);
        ReflectionTestUtils.setField(syncInfo, "latestBlockHeight", 9999L);

        DefaultNodeInfoOther other = new DefaultNodeInfoOther();
        ReflectionTestUtils.setField(other, "txIndex", "txIndex");

        DefaultNodeInfo defaultNodeInfo = new DefaultNodeInfo();
        ReflectionTestUtils.setField(defaultNodeInfo, "other", other);
        ReflectionTestUtils.setField(defaultNodeInfo, "network", "network-1");
        ReflectionTestUtils.setField(defaultNodeInfo, "version", "1.1");
        ReflectionTestUtils.setField(defaultNodeInfo, "moniker", "monkey");
        ReflectionTestUtils.setField(defaultNodeInfo, "defaultNodeID", "12345");

        Status status = new Status();
        ReflectionTestUtils.setField(status, "syncInfo", syncInfo);
        ReflectionTestUtils.setField(status, "nodeInfo", defaultNodeInfo);

        return status;
    }
}