package com.mapofzones.endpointchecker.services.node.rpc;

import com.mapofzones.endpointchecker.domain.NodeRpcAddress;
import com.mapofzones.endpointchecker.services.node.rpc.client.JsonRpcClient;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.NetInfo;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.Peer;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mapofzones.endpointchecker.common.constants.CommonConstants.EMPTY_STRING;
import static com.mapofzones.endpointchecker.common.constants.NodeConstants.RPC_DEFAULT_PORT;

@Service
@Slf4j
public class RpcService implements IRpcService {

    @Override
    public Map<NodeAddressDto, Set<NodeRpcAddress>> checkLivenessAndFindPeers(NodeRpcAddress nodeRpcAddress, Set<String> zoneNames) {
        JsonRpcClient jsonRpcClient = new JsonRpcClient();

        try {
            jsonRpcClient.initiate(nodeRpcAddress.getRpcAddress());
        } catch (MalformedURLException e) {
            return Collections.emptyMap();
        }

        try {
            Status nodeStatus = jsonRpcClient.getNodeStatus();
            checkLiveness(nodeRpcAddress, zoneNames, nodeStatus);
        } catch (Exception e) {
            nodeRpcAddress.setIsAlive(false);
            nodeRpcAddress.setLastCheckedAt(LocalDateTime.now());
            return Collections.emptyMap();
        }

        try {
            NetInfo netInfo = jsonRpcClient.getNetInfo();
            return findPeers(nodeRpcAddress.getZone(), netInfo);
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }

    private void checkLiveness(NodeRpcAddress nodeRpcAddress, Set<String> zoneNames, Status nodeStatus) {

        try {
            String network = nodeStatus.getNodeInfo().getNetwork();
            if (!network.equalsIgnoreCase(nodeRpcAddress.getZone())) {
                if (zoneNames.contains(network)) {
                    nodeRpcAddress.setIsAlive(true);
                    nodeRpcAddress.setLastActive(LocalDateTime.now());
                    nodeRpcAddress.setZone(network);
                } else {
                    nodeRpcAddress.setIsAlive(false);
                }
            } else {
                nodeRpcAddress.setIsAlive(true);
                nodeRpcAddress.setLastActive(LocalDateTime.now());
            }
            nodeRpcAddress.setLastCheckedAt(LocalDateTime.now());
            nodeRpcAddress.setNodeId(nodeStatus.getNodeInfo().getDefaultNodeID());
            nodeRpcAddress.setVersion(nodeStatus.getNodeInfo().getVersion());
            nodeRpcAddress.setTxIndex(nodeStatus.getNodeInfo().getOther().getTxIndex());
            nodeRpcAddress.setMoniker(nodeStatus.getNodeInfo().getMoniker());
            nodeRpcAddress.setLastBlockHeight(nodeStatus.getSyncInfo().getLatestBlockHeight());
            nodeRpcAddress.setEarliestBlockHeight(nodeStatus.getSyncInfo().getEarliestBlockHeight());

        } catch (NullPointerException e) {
//                todo: try request again. for MalformedURLException too?
        } catch (Exception e) {
            nodeRpcAddress.setIsAlive(false);
        }
    }

    private Map<NodeAddressDto, Set<NodeRpcAddress>> findPeers(String zone, NetInfo netInfo) {
        Map<NodeAddressDto, Set<NodeRpcAddress>> foundPeers = new HashMap<>();

        for (Peer peer : netInfo.getPeers()) {

            String rpcAddress = peer.getNodeInfo().getOther().getRpcAddress();
            if (!rpcAddress.isEmpty()) {
                if (isMultiRpcAddress(rpcAddress))
                    rpcAddress = findRpcAddress(rpcAddress);

                if (!rpcAddress.isEmpty()) {

                    String port = findPort(rpcAddress);

                    Map.Entry<NodeAddressDto, NodeRpcAddress> foundPeerEntry = buildNodeRpcAddress(zone, port, peer);
                    if (foundPeers.containsKey(foundPeerEntry.getKey()))
                        foundPeers.get(foundPeerEntry.getKey()).add(foundPeerEntry.getValue());
                    else
                        foundPeers.put(foundPeerEntry.getKey(), Stream.of(foundPeerEntry.getValue()).collect(Collectors.toCollection(HashSet::new)));

                    if (!port.equals(RPC_DEFAULT_PORT)) {
                        NodeRpcAddress extraNodeRpcAddress = buildExtraNodeRpcAddress(foundPeerEntry.getValue());
                        foundPeers.get(foundPeerEntry.getKey()).add(extraNodeRpcAddress);
                    }
                }
            }
        }
        return foundPeers;
    }

    private Map.Entry<NodeAddressDto, NodeRpcAddress> buildNodeRpcAddress(String zone, String port, Peer peer) {

        NodeAddressDto foundNodeAddress = new NodeAddressDto();
        foundNodeAddress.setIpOrDns(peer.getRemoteIP());
        foundNodeAddress.setLastCheckedAt(LocalDateTime.now());

        NodeRpcAddress foundNodeRpcAddress = new NodeRpcAddress();
        foundNodeRpcAddress.setZone(zone);
        foundNodeRpcAddress.setRpcAddress("http://" + peer.getRemoteIP() + ":" + port);
        foundNodeRpcAddress.setNodeAddress(foundNodeAddress.getIpOrDns());
        foundNodeRpcAddress.setLastCheckedAt(LocalDateTime.now());
        foundNodeRpcAddress.setLastActive(LocalDateTime.now());
        foundNodeRpcAddress.setIsAlive(false);
        foundNodeRpcAddress.setIsHidden(false);
        foundNodeRpcAddress.setIsPrioritized(false);
        return new AbstractMap.SimpleEntry<>(foundNodeAddress, foundNodeRpcAddress);
    }

    private NodeRpcAddress buildExtraNodeRpcAddress(NodeRpcAddress foundRpcAddress) {
        NodeRpcAddress extraNodeRpcAddress = (NodeRpcAddress) foundRpcAddress.clone();
        extraNodeRpcAddress.setNodeAddress("http://" + foundRpcAddress.getNodeAddress() + ":" + RPC_DEFAULT_PORT);
        return extraNodeRpcAddress;
    }

    private String findPort(String rpcAddress) {
        String[] arr = rpcAddress.split(":");
        return arr[2];
    }

    private String findRpcAddress(String rpcAddress) {
        String[] addresses = rpcAddress.split(",");

        for (String address : addresses) {
            if (!address.contains("0.0.0.0") || !address.contains("127.0.0.1")) {
                return address;
            }
        }
        return EMPTY_STRING;
    }

    private boolean isMultiRpcAddress (String rpcAddress) {
        return rpcAddress.contains(",");
    }
}
