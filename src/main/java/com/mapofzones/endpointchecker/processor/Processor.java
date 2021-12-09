package com.mapofzones.endpointchecker.processor;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mapofzones.endpointchecker.data.entities.Node;
import com.mapofzones.endpointchecker.data.repositories.NodeRepository;
import com.mapofzones.endpointchecker.data.rpc.ABCIInfo;
import com.mapofzones.endpointchecker.data.rpc.NetInfo;
import com.mapofzones.endpointchecker.data.rpc.Status;
import com.mapofzones.endpointchecker.services.JsonRpcService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class Processor implements Parser {
    private final NodeRepository nodeRepository;

    public Processor(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public void doScript() {
        System.out.println("Starting...");

        List<Node> nodes = new ArrayList<>();
        System.out.println("ready to get nodes");
        nodes.addAll(nodeRepository.getNodes());
        System.out.println("ready to check endpoints");
        nodesLivenessChecker(nodes);
        System.out.println("ready to save nodes");
        nodeRepository.saveAll(nodes);
        System.out.println("Finished!");
        System.out.println("---------------");
    }

    private void nodesLivenessChecker(List<Node> nodes) {
        for (Node node : nodes) {
            JsonRpcService jsonRpcService = new JsonRpcService();
            try {
                if (node.getIp() == null || node.getIp().isEmpty() || node.getIp().isBlank()) {
                    if (node.getAddress() != null && !node.getAddress().isEmpty()) {
                        URI uri = new URI(node.getAddress());
                        String domain = uri.getHost();
                        node.setIp(domain.startsWith("www.") ? domain.substring(4) : domain);
                    }
                }

                jsonRpcService.initiate(node.getAddress());
                ABCIInfo abciInfo = jsonRpcService.getABCIInfo();
                NetInfo netInfo = jsonRpcService.getNetInfo();
                Status nodeStatus = jsonRpcService.getNodeStatus();
                node.setAlive(
                        node.getZone().equalsIgnoreCase(
                                nodeStatus.getNodeInfo().getNetwork()));
//                todo: check db is contains zone
//                node.setZone(nodeStatus.getNodeInfo().getNetwork());
                node.setNodeId(nodeStatus.getNodeInfo().getDefaultNodeID());
                node.setVersion(nodeStatus.getNodeInfo().getVersion());
                node.setTxIndex(nodeStatus.getNodeInfo().getOther().getTxIndex());
                node.setMoniker(nodeStatus.getNodeInfo().getMoniker());
                node.setLastBlockHeight(nodeStatus.getSyncInfo().getLatestBlockHeight());
                node.setRpcAddrActive(true);
//                todo: add peers to check them

            } catch (SocketTimeoutException e) {
                node.setAlive(false);
                node.setRpcAddrActive(false);
            } catch (InvalidFormatException | JsonParseException | NullPointerException e) {
//                todo: try request again. for MalformedURLException too?
            } catch (ConnectException e) {
//                todo: Connection refused logic
            } catch (Exception e) {
//                todo: something
            }



            try {
                String lcd;
                if (node.getLcdAddr() == null || node.getLcdAddr().isEmpty()) {
                    String port = Integer.toString(new URL(node.getAddress()).getPort());
                    lcd = node.getAddress().replace(":" + port, ":1317");
                } else lcd = node.getLcdAddr();
                URL url = new URL(lcd + "/node_info");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                int status = con.getResponseCode();
                if (status == 200) {
                    node.setLcdAddrActive(true);
                    node.setLcdAddr(lcd);
                }
                else node.setLcdAddrActive(false);
                con.disconnect();
            } catch (IOException e) {
                node.setLcdAddrActive(false);
            }

            node.setLastCheckedAt(new Timestamp(System.currentTimeMillis()));
        }
    }

    private String readResponse(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String chainId = Parser.parseChainId(in, "network");
        in.close();
        return chainId;
    }
}
