package com.mapofzones.endpointchecker.services.node.rest;

import com.mapofzones.endpointchecker.domain.NodeRestAddress;
import com.mapofzones.endpointchecker.services.node.rest.client.RestClient;
import com.mapofzones.endpointchecker.services.node.rest.client.dto.NodeInfo;
import com.mapofzones.endpointchecker.services.node.rest.client.dto.NodeInfoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestServiceTest {

    @Test
    void checkLiveliness_isAlive_WithSameZone_Test() {

        RestClient restClient = mock(RestClient.class);

        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setNetwork("network-1");
        NodeInfoDto nodeInfoDto = new NodeInfoDto(true);
        nodeInfoDto.setNodeInfo(nodeInfo);

        when(restClient.findNodeInfo(any())).thenReturn(nodeInfoDto);

        Set<String> zoneNames = Set.of("network-1");

        NodeRestAddress nodeRestAddress = new NodeRestAddress();
        nodeRestAddress.setRestAddress("network-1");

        IRestService lcdService = new RestService(restClient);
        lcdService.checkLiveliness(nodeRestAddress, zoneNames);

        assertTrue(nodeRestAddress.getIsAlive());
    }

    @Test
    void checkLiveliness_isAlive_WithDifferentZone_Test() {

        RestClient restClient = mock(RestClient.class);

        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setNetwork("network-2");
        NodeInfoDto nodeInfoDto = new NodeInfoDto(true);
        nodeInfoDto.setNodeInfo(nodeInfo);

        when(restClient.findNodeInfo(any())).thenReturn(nodeInfoDto);

        Set<String> zoneNames = Set.of("network-1", "network-2");

        NodeRestAddress nodeRestAddress = new NodeRestAddress();
        nodeRestAddress.setRestAddress("network-1");

        IRestService lcdService = new RestService(restClient);
        lcdService.checkLiveliness(nodeRestAddress, zoneNames);

        assertTrue(nodeRestAddress.getIsAlive());
        assertEquals("network-2", nodeRestAddress.getZone());
    }

    @Test
    void checkLiveliness_isNotAlive_Test() {
        RestClient restClient = mock(RestClient.class);

        NodeInfoDto nodeInfoDto = new NodeInfoDto(false);

        when(restClient.findNodeInfo(any())).thenReturn(nodeInfoDto);

        Set<String> zoneNames = Set.of("network-1", "network-2");

        NodeRestAddress nodeRestAddress = new NodeRestAddress();
        nodeRestAddress.setRestAddress("network-1");

        IRestService lcdService = new RestService(restClient);
        lcdService.checkLiveliness(nodeRestAddress, zoneNames);

        assertFalse(nodeRestAddress.getIsAlive());
    }

}
