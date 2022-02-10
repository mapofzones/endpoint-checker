package com.mapofzones.endpointchecker.services.node.lcd.client;

import com.mapofzones.endpointchecker.common.properties.EndpointProperties;
import com.mapofzones.endpointchecker.services.node.lcd.client.dto.NodeInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Slf4j
public class LcdClient {

    private final RestTemplate lcdClientRestTemplate;
    private final EndpointProperties endpointProperties;

    public LcdClient(RestTemplate lcdClientRestTemplate,
                     EndpointProperties endpointProperties) {
        this.lcdClientRestTemplate = lcdClientRestTemplate;
        this.endpointProperties = endpointProperties;
    }

    public NodeInfoDto findNodeInfo(String address) {
        if (!address.isBlank()) {
            URI uri = URI.create(address + endpointProperties.getLcd().getNodeInfo());
            try {
                Optional<NodeInfoDto> receivedNodeInfoDto = Optional.ofNullable(lcdClientRestTemplate.getForEntity(uri, NodeInfoDto.class).getBody());
                NodeInfoDto nodeInfo = receivedNodeInfoDto.orElse(new NodeInfoDto(false));
                nodeInfo.setSuccessReceived(true);
                return nodeInfo;
            } catch (RestClientException e) {
                //log.warn("Request cant be completed. " + uri);
                return new NodeInfoDto(false);
            }
        } else return new NodeInfoDto(false);
    }
}
