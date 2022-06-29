package com.mapofzones.endpointchecker.services;

import com.mapofzones.endpointchecker.common.properties.EndpointCheckerProperties;
import com.mapofzones.endpointchecker.common.threads.IThreadStarter;
import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.node.INodeService;
import com.mapofzones.endpointchecker.services.zone.IZoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class NodesCheckerFacade {

    private final IZoneService zoneService;
    private final INodeService nodeService;
    private final IThreadStarter pathfinderThreadStarter;
    private final EndpointCheckerProperties endpointCheckerProperties;

    private final Set<String> zoneNames = new HashSet<>();
    private final Set<Node> nodes = Collections.synchronizedSet(new HashSet<>());

    private BlockingQueue<Node> nodeQueue;

    public NodesCheckerFacade(IZoneService zoneService,
                              INodeService nodeService,
                              IThreadStarter pathfinderThreadStarter,
                              EndpointCheckerProperties endpointCheckerProperties) {
        this.zoneService = zoneService;
        this.nodeService = nodeService;
        this.pathfinderThreadStarter = pathfinderThreadStarter;
        this.endpointCheckerProperties = endpointCheckerProperties;
    }

    public void checkAll() {
        log.info("Starting...");
        clearOldData();

        log.info("ready to get nodes");
        nodes.addAll(nodeService.findTopOfOldNodes(endpointCheckerProperties.getPageSize()));
        findZoneNames();

        log.info("Ready to check endpoints");
        if (!nodes.isEmpty()) {
            nodeQueue = new ArrayBlockingQueue<>(nodes.size(), true, nodes);
            pathfinderThreadStarter.startThreads(nodesCheckerFunction);
            log.info("Ready to save nodes");
            nodeService.saveAll(new ArrayList<>(nodes));
        }

        log.info("Finished!");
        log.info("---------------");

    }

    public void check(Node node) {
        nodes.addAll(nodeService.checkLivenessAndFindPeers(node, zoneNames));
        node.setLastCheckedAt(new Timestamp(System.currentTimeMillis()));
    }

    private void findZoneNames() {
        if (!zoneNames.isEmpty()) {
            zoneNames.clear();
        }
        zoneNames.addAll(zoneService.findUniqueZoneNames());
    }

    private final Runnable nodesCheckerFunction = () -> nodeQueue.stream().parallel().forEach(node -> {
        try {
            Node currentNode = nodeQueue.take();
            check(currentNode);
        } catch (InterruptedException e) {
            log.error("Queue error. " + e.getCause());
            e.printStackTrace();
        }
    });

    private void clearOldData() {
        if (!nodes.isEmpty()) {
            nodes.clear();
            log.info("Old nodes cleared!!!");
        }
    }
}
