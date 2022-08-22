package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.domain.NodeAddress;
import com.mapofzones.endpointchecker.domain.NodeRestAddress;
import com.mapofzones.endpointchecker.domain.NodeRpcAddress;
import com.mapofzones.endpointchecker.services.node.location.LocationClient;
import com.mapofzones.endpointchecker.services.node.rest.IRestService;
import com.mapofzones.endpointchecker.services.node.rpc.IRpcService;
import com.mapofzones.endpointchecker.services.node.rpc.NodeAddressDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NodeAddressServiceTest {

    @Mock
    private NodeAddressRepository nodeAddressRepository;
    @Mock
    private IRestService restService;
    @Mock
    private IRpcService rpcService;
    @Mock
    private LocationClient locationClient;

    private NodeAddressService nodeAddressService;

    @BeforeEach
    void init() {
        this.nodeAddressService = new NodeAddressService(nodeAddressRepository, restService, rpcService, locationClient);
    }

    @Test
    void findAndSetRestFromRpc_Positive_Test() {
        NodeAddress nodeAddress = getTestNodeAddressWithoutRest();
        ReflectionTestUtils.invokeMethod(nodeAddressService, "findAndSetRestFromRpc", nodeAddress);
        assertAll(() -> {
            assertEquals(1, nodeAddress.getRestAddresses().size());
            assertEquals("http://1.1.1.1:1317", nodeAddress.getRestAddresses().iterator().next().getRestAddress());
        });
    }

    @Test
    void findAndSetRestFromRpc_Negative_Test() {
        NodeAddress nodeAddress = getTestNodeAddressWithRest();
        ReflectionTestUtils.invokeMethod(nodeAddressService, "findAndSetRestFromRpc", nodeAddress);
        assertAll(() -> {
            assertEquals(1, nodeAddress.getRestAddresses().size());
            assertEquals("http://1.1.1.1:1317", nodeAddress.getRestAddresses().iterator().next().getRestAddress());
        });
    }

    @Test
    void checkLivelinessAndFindPeersByRpcAddress_PutPeer_Test() {
        Set<String> zoneNames = Set.of("network-1");
        Map<NodeAddressDto, Set<NodeRpcAddress>> allFoundPeers = new HashMap<>();

        Map<NodeAddressDto, Set<NodeRpcAddress>> foundPeers = new HashMap<>();
        foundPeers.put(getTestNodeAddressDto(), getTestNodeAddressWithoutRest().getRpcAddresses());

        when(rpcService.checkLivelinessAndFindPeers(any(), any())).thenReturn(foundPeers);
        ReflectionTestUtils.invokeMethod(nodeAddressService, "checkLivelinessAndFindPeersByRpcAddress", getTestNodeAddressWithoutRest(), zoneNames, allFoundPeers);
        assertEquals(1, allFoundPeers.size());
    }

    @Test
    void ifNodeAddressNotExistsBuildNewNodeAddressTest() {
        Set<NodeAddress> nodeAddresses = new HashSet<>();

        NodeAddress nodeAddress = getTestNodeAddressWithRest();

        Map<NodeAddressDto, Set<NodeRpcAddress>> foundPeers = new HashMap<>();
        foundPeers.put(getTestNodeAddressDto(), getTestNodeAddressWithoutRest().getRpcAddresses());

        ReflectionTestUtils.invokeMethod(nodeAddressService, "ifNodeAddressNotExistsBuildNewNodeAddress", nodeAddress, foundPeers, nodeAddresses);

        assertAll(() -> {
            assertEquals(1, nodeAddresses.size());
            assertEquals("1.1.1.1", nodeAddresses.iterator().next().getIpOrDns());
        });
    }

    @Test
    void checkLivelinessAndFindPeersByRpcAddress_InvalidAddress_Test() {
        NodeAddress nodeAddress = getTestNodeAddressWithoutRest();
        nodeAddress.getRpcAddresses().iterator().next().setRpcAddress("qwerty123");

        Set<String> zoneNames = Set.of("network-1");
        Map<NodeAddressDto, Set<NodeRpcAddress>> foundPeers = new HashMap<>();
        ReflectionTestUtils.invokeMethod(nodeAddressService, "checkLivelinessAndFindPeersByRpcAddress", nodeAddress, zoneNames, foundPeers);
        assertEquals(0, foundPeers.size());
    }

    NodeAddress getTestNodeAddressWithoutRest() {
        NodeRpcAddress nodeRpcAddress = new NodeRpcAddress();
        nodeRpcAddress.setZone("network-1");
        nodeRpcAddress.setRpcAddress("http://1.1.1.1:1234");
        Set<NodeRpcAddress> rpcAddresses = new HashSet<>(List.of(nodeRpcAddress));

        NodeAddress nodeAddress = new NodeAddress();
        nodeAddress.setIpOrDns("1.1.1.1");
        nodeAddress.setRpcAddresses(rpcAddresses);

        return nodeAddress;
    }

    NodeAddress getTestNodeAddressWithRest() {
        NodeRpcAddress nodeRpcAddress = new NodeRpcAddress();
        nodeRpcAddress.setZone("network-1");
        nodeRpcAddress.setRpcAddress("http://1.1.1.1:1234");
        Set<NodeRpcAddress> rpcAddresses = new HashSet<>(List.of(nodeRpcAddress));

        NodeRestAddress nodeRestAddress = new NodeRestAddress();
        nodeRestAddress.setZone("network-1");
        nodeRestAddress.setRestAddress("http://1.1.1.1:1317");
        Set<NodeRestAddress> restAddresses = new HashSet<>(List.of(nodeRestAddress));

        NodeAddress nodeAddress = new NodeAddress();
        nodeAddress.setIpOrDns("1.1.1.1");
        nodeAddress.setRpcAddresses(rpcAddresses);
        nodeAddress.setRestAddresses(restAddresses);

        return nodeAddress;
    }

    NodeAddressDto getTestNodeAddressDto () {
        NodeAddressDto nodeAddressDto = new NodeAddressDto();
        nodeAddressDto.setIpOrDns("1.1.1.1");
        nodeAddressDto.setLastCheckedAt(LocalDateTime.of(2022, 1, 1, 1, 1));
        return nodeAddressDto;
    }
}