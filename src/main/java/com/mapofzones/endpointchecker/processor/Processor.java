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
            try {
                URL url = new URL(node.getAddress() + "/status");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                int status = con.getResponseCode();
                if (status == 200)
                    node.setAlive(true);
                else node.setAlive(false);

                String chainId = readResponse(con);
                con.disconnect();

                System.out.println(chainId); // todo: set it to the new Node entity
            } catch (IOException e) {
                node.setAlive(false);
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
