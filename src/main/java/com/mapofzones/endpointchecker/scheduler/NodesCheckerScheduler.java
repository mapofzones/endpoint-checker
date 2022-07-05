package com.mapofzones.endpointchecker.scheduler;

import com.mapofzones.endpointchecker.common.threads.IThreadStarter;
import com.mapofzones.endpointchecker.services.NodesCheckerFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NodesCheckerScheduler {

    private final NodesCheckerFacade nodesCheckerFacade;
    private final IThreadStarter nodeCheckerThreadStarter;
    private int endpointCheckerIteration = 0;
    private int findNodeLocationIteration = 0;

    public NodesCheckerScheduler(NodesCheckerFacade nodesCheckerFacade, IThreadStarter nodeCheckerThreadStarter) {
        this.nodesCheckerFacade = nodesCheckerFacade;
        this.nodeCheckerThreadStarter = nodeCheckerThreadStarter;
    }

    @Scheduled(fixedDelayString = "#{endpointCheckerProperties.syncTime}", initialDelay = 10)
    public void checkNodes() {
        if (nodeCheckerThreadStarter.isDone()) {
            endpointCheckerIteration += 1;
            log.info("Start iteration of Endpoint checker: " + endpointCheckerIteration);
            nodesCheckerFacade.checkAll(endpointCheckerIteration);
        }
    }

    @Scheduled(fixedDelayString = "#{locationFinderProperties.syncTime}", initialDelay = 10000)
    public void findNodeLocation() {
        findNodeLocationIteration += 1;
        log.info("Start iteration of Find node location: " + findNodeLocationIteration);
        nodesCheckerFacade.findLocations(findNodeLocationIteration);
    }
}
