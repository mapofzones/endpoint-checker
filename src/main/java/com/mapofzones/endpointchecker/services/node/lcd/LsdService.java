package com.mapofzones.endpointchecker.services.node.lcd;

import com.mapofzones.endpointchecker.domain.NodeLcdAddress;
import com.mapofzones.endpointchecker.services.node.lcd.client.LcdClient;
import com.mapofzones.endpointchecker.services.node.lcd.client.dto.NodeInfoDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class LsdService implements ILcdService {

    private final LcdClient lcdClient;

    public LsdService(LcdClient lcdClient) {
        this.lcdClient = lcdClient;
    }

    @Override
    public void checkLiveness(NodeLcdAddress nodeLcdAddress, Set<String> zoneNames) {
        NodeInfoDto nodeInfoDto = lcdClient.findNodeInfo(nodeLcdAddress.getLcdAddress());

        if (nodeInfoDto.isSuccessReceived()) {
            if (!nodeInfoDto.getNodeInfo().getNetwork().equals(nodeLcdAddress.getZone()) &&
                    zoneNames.contains(nodeInfoDto.getNodeInfo().getNetwork())) {
                nodeLcdAddress.setZone(nodeInfoDto.getNodeInfo().getNetwork());
            }
            nodeLcdAddress.setIsAlive(true);
            nodeLcdAddress.setLastActive(LocalDateTime.now());
        }
    }
}
