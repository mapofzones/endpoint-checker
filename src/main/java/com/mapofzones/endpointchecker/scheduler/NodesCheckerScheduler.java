package com.mapofzones.endpointchecker.scheduler;

import com.mapofzones.endpointchecker.services.NodesCheckerFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NodesCheckerScheduler {

    private final NodesCheckerFacade nodesCheckerFacade;
    private int iteration = 0;

    public NodesCheckerScheduler(NodesCheckerFacade nodesCheckerFacade) {
        this.nodesCheckerFacade = nodesCheckerFacade;
    }

    @Scheduled(fixedDelayString = "#{endpointCheckerProperties.syncTime}", initialDelay = 10)
    public void checkNodes() {
        iteration += 1;
        log.info("Iteration: " + iteration);
        nodesCheckerFacade.checkAll();
    }

    @Scheduled(fixedDelayString = "#{locationFinderProperties.syncTime}", initialDelay = 10000)
    public void findNodeLocation() {
        nodesCheckerFacade.findLocations();
    }
}
