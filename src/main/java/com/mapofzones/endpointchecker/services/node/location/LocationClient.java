package com.mapofzones.endpointchecker.services.node.location;

import com.mapofzones.endpointchecker.common.properties.EndpointProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Slf4j
public class LocationClient {

    private final RestTemplate locationClientRestTemplate;
    private final EndpointProperties endpointProperties;

    public LocationClient(RestTemplate locationClientRestTemplate,
                          EndpointProperties endpointProperties) {
        this.locationClientRestTemplate = locationClientRestTemplate;
        this.endpointProperties = endpointProperties;
    }

    public LocationDto findLocation(String ipOrDns) {
        if (!ipOrDns.isBlank()) {
            try {
                URI uri = URI.create(String.format(endpointProperties.getIpInfo(), ipOrDns));
                Optional<LocationDto> foundLocationDto = Optional.ofNullable(locationClientRestTemplate.getForEntity(uri, LocationDto.class).getBody());
                log.info("Found " + ipOrDns);
                return foundLocationDto.orElse(new LocationDto("fail"));
            } catch (RestClientException e) {
                log.info("Failed " + ipOrDns);
                return new LocationDto("fail");
            }
        } else return new LocationDto("fail");
    }
}
