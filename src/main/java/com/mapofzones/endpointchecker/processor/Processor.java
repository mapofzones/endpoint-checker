package com.mapofzones.endpointchecker.processor;

import com.mapofzones.endpointchecker.data.entities.Node;
import com.mapofzones.endpointchecker.data.repositories.NodeRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Processor {
    private final NodeRepository nodeRepository;

    public Processor(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public void doScript() {
        System.out.println("Starting...");

        List<Node> nodes = new ArrayList<>();
        System.out.println("ready to get nodes");
        nodes.addAll(nodeRepository.getNodes());
//        for (Node node : nodes) {
//            System.out.println(node);
//        }
        System.out.println("ready to check endpoints");
        nodesLivenessChecker(nodes);
        System.out.println("ready to save nodes");
        nodeRepository.saveAll(nodes);
        System.out.println("Finished!");
        System.out.println("---------------");
    }

    private void nodesLivenessChecker(List<Node> nodes) {
        for (Node node : nodes) {
            try {
                URL url = new URL(node.getAddress() + "/status");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                int status = con.getResponseCode();
//                System.out.println("ResponseCode: " + status);
                if (status == 200)
                    node.setAlive(true);
                else node.setAlive(false);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
//                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
//                    content.append(inputLine);
                    if (inputLine.contains("\"network\": \"")) {
//                        System.out.println(inputLine);
                        Pattern pattern = Pattern.compile("\"network\": \"(.*?)\"");
                        Matcher matcher = pattern.matcher(inputLine);
                        if (matcher.find())
                            System.out.println(matcher.group(1));//todo: set it to the new Node entity
                    }
                }

                in.close();
                con.disconnect();
            } catch (IOException e) {
//                e.printStackTrace();
                node.setAlive(false);
            }

            node.setLastCheckedAt(new Timestamp(System.currentTimeMillis()));
//            System.out.println(node);
        }
    }
}
