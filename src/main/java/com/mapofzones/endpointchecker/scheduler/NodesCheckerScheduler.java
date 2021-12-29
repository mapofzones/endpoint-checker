package com.mapofzones.endpointchecker.scheduler;

import com.mapofzones.endpointchecker.services.NodesCheckerFacade;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NodesCheckerScheduler {

    private final NodesCheckerFacade nodesCheckerFacade;

    public NodesCheckerScheduler(NodesCheckerFacade nodesCheckerFacade) {
        this.nodesCheckerFacade = nodesCheckerFacade;
    }

    @Scheduled(fixedDelayString = "#{endpointCheckerProperties.syncTime}", initialDelay = 10)
    public void callDownloader() {
        nodesCheckerFacade.checkAll();
    }
}
