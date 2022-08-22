package com.mapofzones.endpointchecker.services.node.rest.client;

import com.mapofzones.endpointchecker.common.properties.EndpointProperties;
import com.mapofzones.endpointchecker.services.node.rest.client.dto.NodeInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Slf4j
public class RestClient {

    private final RestTemplate restClientRestTemplate;
    private final EndpointProperties endpointProperties;

    public RestClient(RestTemplate restClientRestTemplate,
                      EndpointProperties endpointProperties) {
        this.restClientRestTemplate = restClientRestTemplate;
        this.endpointProperties = endpointProperties;
    }

    public NodeInfoDto findNodeInfo(String address) {
        if (!address.isBlank()) {
            URI uri = URI.create(address + endpointProperties.getRest().getNodeInfo());
            try {
                Optional<NodeInfoDto> receivedNodeInfoDto = Optional.ofNullable(restClientRestTemplate.getForEntity(uri, NodeInfoDto.class).getBody());
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
