package com.mapofzones.endpointchecker.services.node.lcd;

import com.mapofzones.endpointchecker.domain.NodeLcdAddress;
import com.mapofzones.endpointchecker.services.node.lcd.client.LcdClient;
import com.mapofzones.endpointchecker.services.node.lcd.client.dto.NodeInfoDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LsdService implements ILcdService {

    private final LcdClient lcdClient;

    public LsdService(LcdClient lcdClient) {
        this.lcdClient = lcdClient;
    }

    @Override
    public void checkLiveness(NodeLcdAddress nodeLcdAddress) {
        NodeInfoDto nodeInfoDto = lcdClient.findNodeInfo(nodeLcdAddress.getLcdAddress());
        nodeLcdAddress.setIsAlive(nodeInfoDto.isSuccessReceived() && nodeInfoDto.getNodeInfo().getNetwork().equals(nodeLcdAddress.getZone()));
        if (nodeLcdAddress.getIsAlive()) {
            nodeLcdAddress.setLastActive(LocalDateTime.now());
        }
        nodeLcdAddress.setLastCheckedAt(LocalDateTime.now());
    }
}
