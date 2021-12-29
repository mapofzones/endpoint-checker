package com.mapofzones.endpointchecker.services.node.rpc;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.node.rpc.client.JsonRpcClient;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.ABCIInfo;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.NetInfo;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.Status;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.List;
import java.util.Set;

@Service
public class RpcService implements IRpcService {

    @Override
    public void checkLiveness(Node node, Set<String> zoneNames) {

        JsonRpcClient jsonRpcClient = new JsonRpcClient();
        try {
            if (node.getIp() == null || node.getIp().isEmpty() || node.getIp().isBlank()) {
                if (node.getAddress() != null && !node.getAddress().isEmpty()) {
                    URI uri = new URI(node.getAddress());
                    String domain = uri.getHost();
                    node.setIp(domain.startsWith("www.") ? domain.substring(4) : domain);
                }
            }

            jsonRpcClient.initiate(node.getAddress());
            ABCIInfo abciInfo = jsonRpcClient.getABCIInfo();
            NetInfo netInfo = jsonRpcClient.getNetInfo();
            Status nodeStatus = jsonRpcClient.getNodeStatus();

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

        } catch (SocketTimeoutException e) {
            node.setIsAlive(false);
            node.setIsRpcAddrActive(false);
        } catch (InvalidFormatException | JsonParseException | NullPointerException e) {
//                todo: try request again. for MalformedURLException too?
        } catch (ConnectException e) {
//                todo: Connection refused logic
        } catch (Exception e) {
//                todo: something
        }

    }
}
