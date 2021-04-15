package com.mapofzones.endpointchecker.processor;

import com.mapofzones.endpointchecker.data.entities.Node;
import com.mapofzones.endpointchecker.data.repositories.NodeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Processor {
    private final NodeRepository nodeRepository;

    public Processor(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public void doScript() {
        System.out.println("Starting...");

        List<Node> nodes = new ArrayList<>();
        nodes.addAll(nodeRepository.getNodes());
        for (Node node : nodes) {
            System.out.println(node);
        }

        System.out.println("Finished!");
        System.out.println("---------------");
    }
}
