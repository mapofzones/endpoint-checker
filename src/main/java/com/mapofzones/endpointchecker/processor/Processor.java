package com.mapofzones.endpointchecker.processor;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mapofzones.endpointchecker.common.constants.NodeConstants;
import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.domain.Zone;
import com.mapofzones.endpointchecker.services.node.NodeRepository;
import com.mapofzones.endpointchecker.services.node.rpc.Parser;
import com.mapofzones.endpointchecker.services.zone.ZoneRepository;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.ABCIInfo;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.NetInfo;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.Status;
import com.mapofzones.endpointchecker.services.node.rpc.client.JsonRpcClient;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Processor implements Parser {
    private final NodeRepository nodeRepository;
    private final ZoneRepository zoneRepository;

    public Processor(NodeRepository nodeRepository, ZoneRepository zoneRepository) {
        this.nodeRepository = nodeRepository;
        this.zoneRepository = zoneRepository;
    }

    public void doScript() {
        System.out.println("Starting...");

        List<Node> nodes = new ArrayList<>();
        Set<String> zones;
        System.out.println("ready to get nodes");
        nodes.addAll(nodeRepository.getNodes());
        zones = zoneRepository.getZoneNames()
                .stream()
                .map(Zone::getChainId)
                .collect(Collectors.toSet());
        System.out.println("ready to check endpoints");
        nodesLivenessChecker(nodes, zones);
        System.out.println("ready to save nodes");
        nodeRepository.saveAll(nodes);
        System.out.println("Finished!");
        System.out.println("---------------");
    }

    private void nodesLivenessChecker(List<Node> nodes, Set<String> zones) {
//        for (Node node : nodes) {
//            JsonRpcClient jsonRpcClient = new JsonRpcClient();
//            try {
//                if (node.getIp() == null || node.getIp().isEmpty() || node.getIp().isBlank()) {
//                    if (node.getAddress() != null && !node.getAddress().isEmpty()) {
//                        URI uri = new URI(node.getAddress());
//                        String domain = uri.getHost();
//                        node.setIp(domain.startsWith("www.") ? domain.substring(4) : domain);
//                    }
//                }
//
//                jsonRpcClient.initiate(node.getAddress());
//                ABCIInfo abciInfo = jsonRpcClient.getABCIInfo();
//                NetInfo netInfo = jsonRpcClient.getNetInfo();
//                Status nodeStatus = jsonRpcClient.getNodeStatus();
//
//                String network = nodeStatus.getNodeInfo().getNetwork();
//                if (!network.equalsIgnoreCase(node.getZone())) {
//                    if (zones.contains(network)) {
//                        node.setIsAlive(true);
//                        node.setIsRpcAddrActive(true);
//                        node.setZone(network);
//                    } else {
//                        node.setIsAlive(false);
//                        node.setIsRpcAddrActive(false);
//                    }
//                } else {
//                    node.setIsAlive(true);
//                    node.setIsRpcAddrActive(true);
//                }
//                node.setNodeId(nodeStatus.getNodeInfo().getDefaultNodeID());
//                node.setVersion(nodeStatus.getNodeInfo().getVersion());
//                node.setTxIndex(nodeStatus.getNodeInfo().getOther().getTxIndex());
//                node.setMoniker(nodeStatus.getNodeInfo().getMoniker());
//                node.setLastBlockHeight(nodeStatus.getSyncInfo().getLatestBlockHeight());
////                todo: add peers to check them
//
//            } catch (SocketTimeoutException e) {
//                node.setIsAlive(false);
//                node.setIsRpcAddrActive(false);
//            } catch (InvalidFormatException | JsonParseException | NullPointerException e) {
////                todo: try request again. for MalformedURLException too?
//            } catch (ConnectException e) {
////                todo: Connection refused logic
//            } catch (Exception e) {
////                todo: something
//            }
//
//            try {
//                String lcd;
//                if (node.getLcdAddr() == null || node.getLcdAddr().isEmpty()) {
//                    String port = Integer.toString(new URL(node.getAddress()).getPort());
//                    lcd = node.getAddress().replace(":" + port, ":" + NodeConstants.LCD_DEFAULT_PORT);
//                } else lcd = node.getLcdAddr();
//                URL url = new URL(lcd + "/node_info");
//                HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                con.setRequestMethod("GET");
//                con.setConnectTimeout(5000);
//                con.setReadTimeout(5000);
//                int status = con.getResponseCode();
//                if (status == 200) {
//                    node.setIsLcdAddrActive(true);
//                    node.setLcdAddr(lcd);
//                }
//                else node.setIsLcdAddrActive(false);
//                con.disconnect();
//            } catch (IOException e) {
//                node.setIsLcdAddrActive(false);
//            }
//
//            node.setLastCheckedAt(new Timestamp(System.currentTimeMillis()));
//        }
    }

    private String readResponse(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String chainId = Parser.parseChainId(in, "network");
        in.close();
        return chainId;
    }
}
