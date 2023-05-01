package com.mapofzones.endpointchecker.services.node.lcd.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mapofzones.endpointchecker.common.properties.EndpointProperties;
import com.mapofzones.endpointchecker.services.node.lcd.client.dto.NodeInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public class LcdClient {

    private final RestTemplate lcdClientRestTemplate;

    private List<String> nodeInfoEndpoints;
    private List<String> blocksLatestEndpoints;

    public LcdClient(RestTemplate lcdClientRestTemplate,
                     EndpointProperties endpointProperties) {
        this.lcdClientRestTemplate = lcdClientRestTemplate;
        this.nodeInfoEndpoints =
                Arrays.asList(
                        endpointProperties.getLcd().getNewNodeInfo(),
                        endpointProperties.getLcd().getNodeInfo());
        this.blocksLatestEndpoints =
                Arrays.asList(
                        endpointProperties.getLcd().getNewBlocksLatest(),
                        endpointProperties.getLcd().getBlocksLatest());
    }

    public NodeInfoDto findNodeInfo(String address) {
        if (!address.isBlank()) {
            NodeInfoDto nodeInfoDto;

            for (String endpoint : nodeInfoEndpoints) {
                URI uri = URI.create(address.trim() + endpoint);
                nodeInfoDto = findNodeInfo(uri);
                if (nodeInfoDto.isSuccessReceived())
                    return nodeInfoDto;
            }

        }
        return new NodeInfoDto(false);
    }

    public Integer findLastBlockHeight(String address) {
        if (!address.isBlank()) {
            Integer blockLatest;

            for (String endpoint : blocksLatestEndpoints) {
                URI uri = URI.create(address + endpoint);
                blockLatest = findLastBlockHeight(uri);

                if (blockLatest != -1) {
                    return blockLatest;
                }
            }
        }
        return -1;
    }

    private NodeInfoDto findNodeInfo(URI uri) {
        try {
            Optional<NodeInfoDto> receivedNodeInfoDto = Optional.ofNullable(lcdClientRestTemplate.getForEntity(uri, NodeInfoDto.class).getBody());
            NodeInfoDto nodeInfo = receivedNodeInfoDto.orElse(new NodeInfoDto(false));

            nodeInfo.setSuccessReceived(true);

            //log.debug("Request was completed: " + uri);
            return nodeInfo;
        } catch (RestClientException | IllegalArgumentException e) {
            //log.warn("Request cant be completed. " + uri);
            return new NodeInfoDto(false);
        }
    }

    private Integer findLastBlockHeight(URI uri) {
        try {
            Optional<String> receivedJson = Optional.ofNullable(lcdClientRestTemplate.getForEntity(uri, String.class).getBody());
            String json = receivedJson.orElse(null);

            if (json != null) {
                ObjectNode node = new ObjectMapper().readValue(json, ObjectNode.class);
                return node.get("block").get("header").get("height").asInt();
            } else return -1;

        } catch (Exception x) {
            return -1;
        }
    }

}
