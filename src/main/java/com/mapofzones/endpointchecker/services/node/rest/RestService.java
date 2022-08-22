package com.mapofzones.endpointchecker.services.node.rest;

import com.mapofzones.endpointchecker.domain.NodeRestAddress;
import com.mapofzones.endpointchecker.services.node.rest.client.RestClient;
import com.mapofzones.endpointchecker.services.node.rest.client.dto.NodeInfoDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class RestService implements IRestService {

    private final RestClient restClient;

    public RestService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void checkLiveliness(NodeRestAddress nodeRestAddress, Set<String> zoneNames) {
        NodeInfoDto nodeInfoDto = restClient.findNodeInfo(nodeRestAddress.getRestAddress());

        if (nodeInfoDto.isSuccessReceived()) {
            if (!nodeInfoDto.getNodeInfo().getNetwork().equals(nodeRestAddress.getZone()) && zoneNames.contains(nodeInfoDto.getNodeInfo().getNetwork()))
                nodeRestAddress.setZone(nodeInfoDto.getNodeInfo().getNetwork());
            nodeRestAddress.setIsAlive(true);
        } else {
            nodeRestAddress.setIsAlive(false);
        }
        nodeRestAddress.setLastActive(LocalDateTime.now());
    }
}
