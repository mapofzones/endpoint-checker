package com.mapofzones.endpointchecker.services;

import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.node.INodeService;
import com.mapofzones.endpointchecker.services.node.lcd.ILcdService;
import com.mapofzones.endpointchecker.services.node.rpc.IRpcService;
import com.mapofzones.endpointchecker.services.zone.IZoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class NodesCheckerFacade {

    private final IZoneService zoneService;
    private final INodeService nodeService;
    private final IRpcService rpcService;
    private final ILcdService lcdService;

    private final Set<String> zoneNames = new HashSet<>();

    public NodesCheckerFacade(IZoneService zoneService,
                              INodeService nodeService,
                              IRpcService rpcService,
                              ILcdService lcdService) {
        this.zoneService = zoneService;
        this.nodeService = nodeService;
        this.rpcService = rpcService;
        this.lcdService = lcdService;
    }

    public void checkAll() {
        log.info("Starting...");
        log.info("ready to get nodes");
        List<Node> nodes = nodeService.findAll();
        findZoneNames();
        log.info("ready to check endpoints");
        for (Node node : nodes) {
            check(node);
            log.info("Checked: " + node.getAddress());
        }
        log.info("ready to save nodes");
        nodeService.saveAll(nodes);
        log.info("Finished!");
        log.info("---------------");

    }

    public void check(Node node) {
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

    // TODO: Need multithreading
    private final Runnable nodesCheckerFunction = () -> {

    };
}
