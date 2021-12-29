package com.mapofzones.endpointchecker.services;

import com.mapofzones.endpointchecker.common.threads.IThreadStarter;
import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.node.INodeService;
import com.mapofzones.endpointchecker.services.node.lcd.ILcdService;
import com.mapofzones.endpointchecker.services.node.rpc.IRpcService;
import com.mapofzones.endpointchecker.services.zone.IZoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
@Slf4j
public class NodesCheckerFacade {

    private final IZoneService zoneService;
    private final INodeService nodeService;
    private final IRpcService rpcService;
    private final ILcdService lcdService;
    private final IThreadStarter pathfinderThreadStarter;

    private final Set<String> zoneNames = new HashSet<>();
    private final List<Node> checkedNodes = Collections.synchronizedList(new ArrayList<>());

    private BlockingQueue<Node> nodeQueue;

    public NodesCheckerFacade(IZoneService zoneService,
                              INodeService nodeService,
                              IRpcService rpcService,
                              ILcdService lcdService,
                              IThreadStarter pathfinderThreadStarter) {
        this.zoneService = zoneService;
        this.nodeService = nodeService;
        this.rpcService = rpcService;
        this.lcdService = lcdService;
        this.pathfinderThreadStarter = pathfinderThreadStarter;
    }

    public void checkAll() {
        log.info("Starting...");
        if (!checkedNodes.isEmpty()) {
            checkedNodes.clear();
            log.info("Old nodes cleared!!!");
        }

        log.info("ready to get nodes");
        checkedNodes.addAll(nodeService.findAll());
        findZoneNames();

        log.info("Ready to check endpoints");
        if (!checkedNodes.isEmpty()) {
            nodeQueue = new ArrayBlockingQueue<>(checkedNodes.size(), true, checkedNodes);
            pathfinderThreadStarter.startThreads(nodesCheckerFunction);
            pathfinderThreadStarter.waitMainThread();
            log.info("ready to save nodes");
            nodeService.saveAll(checkedNodes);
        }
        log.info("Finished!");
        log.info("---------------");

    }

    @Transactional
    public void check(Node node) {
        log.info("Check:" + node.getAddress());
        rpcService.checkLiveness(node, zoneNames);
        lcdService.checkLiveness(node);
        node.setLastCheckedAt(new Timestamp(System.currentTimeMillis()));
    }

    private void findZoneNames() {
        if (!zoneNames.isEmpty()) {
            zoneNames.clear();
        }
        zoneNames.addAll(zoneService.findUniqueZoneNames());
    }

    private final Runnable nodesCheckerFunction = () -> {
        while (true) {
            if (!nodeQueue.isEmpty()) {
                try {
                    Node currentNode = nodeQueue.take();
                    check(currentNode);
                } catch (InterruptedException e) {
                    log.error("Queue error. " + e.getCause());
                    e.printStackTrace();
                }
            }
            else break;
        }
    };
}
