package com.mapofzones.endpointchecker.services.node;

import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.base.GenericService;
import com.mapofzones.endpointchecker.services.node.lcd.ILcdService;
import com.mapofzones.endpointchecker.services.node.rpc.IRpcService;
import com.mapofzones.endpointchecker.services.node.rpc.client.JsonRpcClient;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.NetInfo;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.Peer;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.Status;
import com.mapofzones.endpointchecker.utils.URLHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static com.mapofzones.endpointchecker.common.constants.NodeConstants.RPC_DEFAULT_PORT;

@Service
@Slf4j
public class NodeService extends GenericService<Node, String, NodeRepository> implements INodeService {

    private final ILcdService lcdService;
    private final IRpcService rpcService;

    public NodeService(NodeRepository repository,
                       ILcdService lcdService,
                       IRpcService rpcService) {
        super(repository);
        this.lcdService = lcdService;
        this.rpcService = rpcService;
    }

    @Override
    public Set<Node> checkLivenessAndFindPeers(Node node, Set<String> zoneNames) {
        JsonRpcClient jsonRpcClient = new JsonRpcClient();

        try {
            jsonRpcClient.initiate(node.getAddress());
        } catch (MalformedURLException e) {
            return new HashSet<>();
        }

        //---- TODO: Need refactoring. To RpcService
        try {
            Status nodeStatus = jsonRpcClient.getNodeStatus();
            rpcService.checkLiveness(node, zoneNames, nodeStatus);
        } catch (Exception e) {
            log.warn("Status of \"" + node.getZone() + "\" (" + node.getAddress() + ") impossible to get.");
            node.setIsAlive(false);
            node.setIsRpcAddrActive(false);
            return new HashSet<>();
        }
        //----

        lcdService.checkLiveness(node);

        try {
            NetInfo netInfo = jsonRpcClient.getNetInfo();
            return findPeers(node.getZone(), netInfo);
        } catch (Exception e) {
            log.warn("NetInfo of \"" + node.getZone() + "\" (" + node.getAddress() + ") impossible to get.");
            return new HashSet<>();
        }
    }

    private Set<Node> findPeers(String zone, NetInfo netInfo) {

        Set<Node> foundPeers = new HashSet<>();
        for (Peer peer : netInfo.getPeers()) {

            String rpcAddress = peer.getNodeInfo().getOther().getRpcAddress();
            if (!rpcAddress.isEmpty()) {
                if (isMultiRpcAddress(rpcAddress))
                    rpcAddress = findRpcAddress(rpcAddress);

                if (!rpcAddress.isEmpty()) {

                    String port = findPort(rpcAddress);

                    Node foundPeer = buildNode(zone, port, peer);
                    foundPeers.add(foundPeer);

                    if (!port.equals(RPC_DEFAULT_PORT)) {
                        Node extraNode = buildExtraNode(foundPeer);
                        foundPeers.add(extraNode);
                    }
                }
            }
        }
        return foundPeers;
    }

    private Node buildNode(String zone, String port, Peer peer) {
        Node foundPeer = new Node();
        foundPeer.setZone(zone);
        foundPeer.setAddress("http://" + peer.getRemoteIP() + ":" + port);
        foundPeer.setIp(peer.getRemoteIP());
        foundPeer.setLastCheckedAt(new Timestamp(System.currentTimeMillis()));
        foundPeer.setIsAlive(false);
        return foundPeer;
    }

    private Node buildExtraNode(Node foundPeer) {
        Node extraNode = (Node) foundPeer.clone();
        extraNode.setAddress("http://" + foundPeer.getIp() + ":" + RPC_DEFAULT_PORT);
        return extraNode;
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
        return "";
    }

    private boolean isMultiRpcAddress (String rpcAddress) {
        return rpcAddress.contains(",");
    }
}
