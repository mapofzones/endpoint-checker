package com.mapofzones.endpointchecker.services.node.rpc;

import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.Status;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Set;

@Service
public class RpcService implements IRpcService {

    @Override
    public void checkLiveness(Node node, Set<String> zoneNames, Status nodeStatus) {

        try {
            if (node.getIp() == null || node.getIp().isEmpty() || node.getIp().isBlank()) {
                if (!node.getAddress().isEmpty()) {
                    URI uri = new URI(node.getAddress());
                    String domain = uri.getHost();
                    node.setIp(domain.startsWith("www.") ? domain.substring(4) : domain);
                }
            }

            String network = nodeStatus.getNodeInfo().getNetwork();
            if (!network.equalsIgnoreCase(node.getZone())) {
                if (zoneNames.contains(network)) {
                    node.setIsAlive(true);
                    node.setIsRpcAddrActive(true);
                    node.setZone(network);
                } else {
                    node.setIsAlive(false);
                    node.setIsRpcAddrActive(false);
                }
            } else {
                node.setIsAlive(true);
                node.setIsRpcAddrActive(true);
            }
            node.setNodeId(nodeStatus.getNodeInfo().getDefaultNodeID());
            node.setVersion(nodeStatus.getNodeInfo().getVersion());
            node.setTxIndex(nodeStatus.getNodeInfo().getOther().getTxIndex());
            node.setMoniker(nodeStatus.getNodeInfo().getMoniker());
            node.setLastBlockHeight(nodeStatus.getSyncInfo().getLatestBlockHeight());
//                todo: add peers to check them

        } catch (NullPointerException e) {
//                todo: try request again. for MalformedURLException too?
        } catch (Exception e) {
            node.setIsAlive(false);
            node.setIsRpcAddrActive(false);
        }
    }
}
