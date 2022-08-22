package com.mapofzones.endpointchecker.config;

import com.mapofzones.endpointchecker.common.properties.EndpointProperties;
import com.mapofzones.endpointchecker.services.node.rest.client.RestClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Configuration
public class RestClientConf {

    @Bean
    public RestTemplate restClientRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
    }

    @Bean
    public RestClient restClient(RestTemplate restClientRestTemplate,
                                 EndpointProperties endpointProperties) {
        return new RestClient(restClientRestTemplate, endpointProperties);
    }
}
