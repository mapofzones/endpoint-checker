package com.mapofzones.endpointchecker;

import com.mapofzones.endpointchecker.config.LcdClientConf;
import com.mapofzones.endpointchecker.config.TestConfig;
import com.mapofzones.endpointchecker.services.node.lcd.client.LcdClient;
import com.mapofzones.endpointchecker.services.node.lcd.client.dto.NodeInfoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureWebClient
@ContextConfiguration(classes = {LcdClientConf.class, TestConfig.class})
class LcdClientTest extends AbstractTest {

    @Autowired
    private LcdClient lcdClient;

    @Test
    void findNodeInfoTest_isAlive() {
        String testedAddress = "https://quicksilver.api.kjnodes.com";
        NodeInfoDto dto = lcdClient.findNodeInfo(testedAddress);
        assertTrue(dto.isSuccessReceived());
        assertEquals(dto.getNodeInfo().getNetwork(), "quicksilver-2");
        assertEquals(dto.getNodeInfo().getVersion(), "0.34.27");
    }

    @Test
    void findNodeInfoTest_isNotAlive() {
        String testedAddress = "http://43.153.101.11:1317";
        NodeInfoDto dto = lcdClient.findNodeInfo(testedAddress);
        assertFalse(dto.isSuccessReceived());
    }

    @Test
    void findBlockLatestTest_isAlive() {
        String testedAddress = "http://51.89.6.150:1317";
        Integer blockHeight = lcdClient.findLastBlockHeight(testedAddress);
        assertEquals(10856304, blockHeight);
    }
}