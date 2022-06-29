package com.mapofzones.endpointchecker.services.node.lcd.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
            try {
                URI uri = URI.create(address + endpointProperties.getLcd().getNodeInfo());
                Optional<NodeInfoDto> receivedNodeInfoDto = Optional.ofNullable(lcdClientRestTemplate.getForEntity(uri, NodeInfoDto.class).getBody());
                NodeInfoDto nodeInfo = receivedNodeInfoDto.orElse(new NodeInfoDto(false));
                nodeInfo.setSuccessReceived(true);
                //log.warn("Request was completed: " + uri);
                return nodeInfo;
            } catch (RestClientException | IllegalArgumentException e) {
                //log.warn("Request cant be completed. " + uri);
                return new NodeInfoDto(false);
            }
        } else return new NodeInfoDto(false);
    }

    public Long findLastBlockHeight(String address) {
        try {
            URI uri = URI.create(address + endpointProperties.getLcd().getBlocksLatest());
            Optional<String> receivedJson = Optional.ofNullable(lcdClientRestTemplate.getForEntity(uri, String.class).getBody());
            String json = receivedJson.orElse(null);

            if (json != null) {
                ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
                return node.get("block").get("header").get("height").asLong();
            } else return 0L;

        } catch (Exception x) {
            return 0L;
        }
    }
}
