package com.mapofzones.endpointchecker.services.node.rest.client;

import com.mapofzones.endpointchecker.AbstractTest;
import com.mapofzones.endpointchecker.config.RestClientConf;
import com.mapofzones.endpointchecker.config.TestConfig;
import com.mapofzones.endpointchecker.services.node.rest.client.dto.NodeInfoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureWebClient
@ContextConfiguration(classes = {RestClientConf.class, TestConfig.class})
class RestClientTest extends AbstractTest {

    @Autowired
    private RestClient restClient;

    @Test
    void findNodeInfoTest_isAlive() {
        String testedAddress = "http://43.153.101.118:1317";
        NodeInfoDto dto = restClient.findNodeInfo(testedAddress);
        assertTrue(dto.isSuccessReceived());
        assertEquals(dto.getNodeInfo().getNetwork(), "osmosis-1");
        assertEquals(dto.getNodeInfo().getVersion(), "0.34.19");
    }

    @Test
    void findNodeInfoTest_isNotAlive() {
        String testedAddress = "http://43.153.101.11:1317";
        NodeInfoDto dto = restClient.findNodeInfo(testedAddress);
        assertFalse(dto.isSuccessReceived());
    }
}