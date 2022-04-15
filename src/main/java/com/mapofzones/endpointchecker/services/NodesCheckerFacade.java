package com.mapofzones.endpointchecker.services;

import com.mapofzones.endpointchecker.common.dto.TimeIntervalsDto;
import com.mapofzones.endpointchecker.common.properties.EndpointCheckerProperties;
import com.mapofzones.endpointchecker.common.properties.LocationFinderProperties;
import com.mapofzones.endpointchecker.common.threads.IThreadStarter;
import com.mapofzones.endpointchecker.domain.NodeAddress;
import com.mapofzones.endpointchecker.services.node.INodeAddressService;
import com.mapofzones.endpointchecker.services.zone.IZoneService;
import com.mapofzones.endpointchecker.utils.TimeIntervalsHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Slf4j
@Component
public class NodesCheckerFacade {

    private final IZoneService zoneService;
    private final INodeAddressService nodeAddressService;
    private final IThreadStarter pathfinderThreadStarter;
    private final EndpointCheckerProperties endpointCheckerProperties;
    private final LocationFinderProperties locationFinderProperties;

    private final Set<String> zoneNames = new HashSet<>();
    private final Set<NodeAddress> nodeAddresses = Collections.synchronizedSet(new HashSet<>());

    private BlockingQueue<NodeAddress> nodeAddressQueue;

    public NodesCheckerFacade(IZoneService zoneService,
                              INodeAddressService nodeAddressService,
                              IThreadStarter pathfinderThreadStarter,
                              EndpointCheckerProperties endpointCheckerProperties,
                              LocationFinderProperties locationFinderProperties) {
        this.zoneService = zoneService;
        this.nodeAddressService = nodeAddressService;
        this.pathfinderThreadStarter = pathfinderThreadStarter;
        this.endpointCheckerProperties = endpointCheckerProperties;
        this.locationFinderProperties = locationFinderProperties;
    }

    public void checkAll() {
        log.info("Starting...");
        clearOldData();

        log.info("ready to get nodes");
        TimeIntervalsDto timeIntervalsDto = TimeIntervalsHelper.parseTimeIntervals(endpointCheckerProperties.getTimeIntervals());
        for (TimeIntervalsDto.TimeInterval interval : timeIntervalsDto.getTimeIntervals()) {
            nodeAddresses.addAll(nodeAddressService.findTopOfOldNodesByTime(interval.getDateTimeFrom(), interval.getDateTimeTo(), interval.getTimeToCheck(), interval.getPageSize()));
        }
        findZoneNames();

        log.info("Ready to check endpoints");
        if (!nodeAddresses.isEmpty()) {
            nodeAddressQueue = new ArrayBlockingQueue<>(nodeAddresses.size(), true, nodeAddresses);
            pathfinderThreadStarter.startThreads(nodesCheckerFunction);
            pathfinderThreadStarter.waitMainThread();

            log.info("Ready to save nodes");
            nodeAddressService.saveAll(new ArrayList<>(nodeAddresses));
        }

        log.info("Finished!");
        log.info("---------------");

    }

    public void check(NodeAddress nodeAddress) {
        nodeAddresses.addAll(nodeAddressService.checkLivenessAndFindPeers(nodeAddress, zoneNames));
        nodeAddress.setLastCheckedAt(LocalDateTime.now());
    }

    public void findLocations() {
        nodeAddressService.findLocations(LocalDateTime.now().minus(locationFinderProperties.getLocationFrequencyCheck().toMillis(), ChronoUnit.MILLIS));
    }

    private void findZoneNames() {
        if (!zoneNames.isEmpty()) {
            zoneNames.clear();
        }
        zoneNames.addAll(zoneService.findUniqueZoneNames());
    }

    private final Runnable nodesCheckerFunction = () -> {
        while (true) {
            if (!nodeAddressQueue.isEmpty()) {
                try {
                    NodeAddress currentNode = nodeAddressQueue.take();
                    check(currentNode);
                    log.info(currentNode.getIpOrDns() + "\t{}", "-- was checked");
                } catch (InterruptedException e) {
                    log.error("Queue error. " + e.getCause());
                    e.printStackTrace();
                }
            }
            else break;
        }
    };

    private void clearOldData() {
        if (!nodeAddresses.isEmpty()) {
            nodeAddresses.clear();
            log.info("Old nodes cleared!!!");
        }
    }
}
