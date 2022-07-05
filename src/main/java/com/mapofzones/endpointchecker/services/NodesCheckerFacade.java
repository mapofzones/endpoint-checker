package com.mapofzones.endpointchecker.services;

import com.mapofzones.endpointchecker.common.dto.TimeIntervalsDto;
import com.mapofzones.endpointchecker.common.properties.EndpointCheckerProperties;
import com.mapofzones.endpointchecker.common.properties.LocationFinderProperties;
import com.mapofzones.endpointchecker.common.threads.IThreadStarter;
import com.mapofzones.endpointchecker.domain.NodeAddress;
import com.mapofzones.endpointchecker.domain.NodeLcdAddress;
import com.mapofzones.endpointchecker.domain.NodeRpcAddress;
import com.mapofzones.endpointchecker.services.node.INodeAddressService;
import com.mapofzones.endpointchecker.services.zone.IZoneService;
import com.mapofzones.endpointchecker.utils.TimeIntervalsHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

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

    @Async
    public void checkAll(int iteration) {
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

            log.info("Ready to save nodes");

            /* TODO: Need to change from save() to saveAll later.
                When we try invoking saveAll() some entries of Set can't be saved, because ipOrDns in the Rpc address is null, but it's not
             */
            for (NodeAddress addr : nodeAddresses) {
                try {
                    nodeAddressService.save(addr);
                } catch (DataIntegrityViolationException e) {
                    String rpc = addr.getRpcAddresses().stream().map(NodeRpcAddress::getRpcAddress).collect(Collectors.joining(",", "{", "}"));
                    String lcd = addr.getLcdAddresses().stream().map(NodeLcdAddress::getLcdAddress).collect(Collectors.joining(",", "{", "}"));
                    log.error(String.format("Cant save: %s (RPC: %s, LCD:%s)", addr.getIpOrDns(), rpc, lcd));
                }
            }
            log.info("Finish iteration of Endpoint checker: " + iteration);
        }
    }

    public void check(NodeAddress nodeAddress) {
        nodeAddresses.addAll(nodeAddressService.checkLivenessAndFindPeers(nodeAddress, zoneNames));
        nodeAddress.setLastCheckedAt(LocalDateTime.now());
    }

    @Async
    public void findLocations(int iteration) {
        nodeAddressService.findLocations(LocalDateTime.now().minus(locationFinderProperties.getLocationFrequencyCheck().toMillis(), ChronoUnit.MILLIS));
        log.info("Finish iteration of Find node location  " + iteration);
    }

    private void findZoneNames() {
        if (!zoneNames.isEmpty()) {
            zoneNames.clear();
        }
        zoneNames.addAll(zoneService.findUniqueZoneNames());
    }

    private final Runnable nodesCheckerFunction = () -> nodeAddressQueue.stream().parallel().forEach(node -> {
        try {
            NodeAddress currentNodeAddress = nodeAddressQueue.take();
            check(currentNodeAddress);
        } catch (InterruptedException e) {
            log.error("Queue error. " + e.getCause());
            e.printStackTrace();
        }
    });

    private void clearOldData() {
        if (!nodeAddresses.isEmpty()) {
            nodeAddresses.clear();
            log.info("Old nodes cleared!!!");
        }
    }
}
