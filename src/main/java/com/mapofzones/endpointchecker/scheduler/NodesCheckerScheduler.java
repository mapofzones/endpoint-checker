package com.mapofzones.endpointchecker.scheduler;

import com.mapofzones.endpointchecker.processor.Processor;
import com.mapofzones.endpointchecker.services.NodesCheckerFacade;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NodesCheckerScheduler {

    private final Processor processor;
    private final NodesCheckerFacade nodesCheckerFacade;

    public NodesCheckerScheduler(Processor processor, NodesCheckerFacade nodesCheckerFacade) {
        this.processor = processor;
        this.nodesCheckerFacade = nodesCheckerFacade;
    }

    @Scheduled(fixedDelayString = "${adaptor.sync-time}", initialDelay = 10000)
    public void callDownloader() {
        //processor.doScript();
        nodesCheckerFacade.checkAll();
    }
}
