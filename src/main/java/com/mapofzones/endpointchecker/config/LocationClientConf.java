package com.mapofzones.endpointchecker.config;

import com.mapofzones.endpointchecker.common.properties.EndpointProperties;
import com.mapofzones.endpointchecker.services.node.location.LocationClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class LocationClientConf {

    @Bean
    public RestTemplate locationClientRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Bean
    public LocationClient locationClient(RestTemplate locationClientRestTemplate,
                                         EndpointProperties endpointProperties) {
        return new LocationClient(locationClientRestTemplate, endpointProperties);
    }
}
