package com.mapofzones.endpointchecker.services.node.lcd;

import com.mapofzones.endpointchecker.common.constants.NodeConstants;
import com.mapofzones.endpointchecker.domain.Node;
import com.mapofzones.endpointchecker.services.node.lcd.client.LcdClient;
import com.mapofzones.endpointchecker.services.node.lcd.client.dto.NodeInfoDto;
import com.mapofzones.endpointchecker.utils.URLHelper;
import org.springframework.stereotype.Service;

@Service
public class LsdService implements ILcdService {

    private final LcdClient lcdClient;

    public LsdService(LcdClient lcdClient) {
        this.lcdClient = lcdClient;
    }

    @Override
    public void checkLiveness(Node node) {

        String lcdAddress;

        if (node.getLcdAddr() == null || node.getLcdAddr().isEmpty()) {
            String port = URLHelper.getPort(node.getAddress());
            lcdAddress = node.getAddress().replace(":" + port, ":" + NodeConstants.LCD_DEFAULT_PORT);
        } else  {
            lcdAddress = node.getLcdAddr();
        }

        NodeInfoDto nodeInfoDto = lcdClient.findNodeInfo(lcdAddress);

        if (nodeInfoDto.isSuccessReceived() && nodeInfoDto.getNodeInfo().getNetwork().equals(node.getZone())) {
            node.setLcdAddr(lcdAddress);
            node.setIsLcdAddrActive(true);
        } else {
            node.setIsLcdAddrActive(false);
        }

        // TODO: set setLastCheckedAt before refactoring tables of database. (Now it set in RPC checkLiveness)
        //node.setLastCheckedAt(new Timestamp(System.currentTimeMillis()));
    }
}
