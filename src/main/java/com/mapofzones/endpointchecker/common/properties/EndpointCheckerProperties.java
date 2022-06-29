package com.mapofzones.endpointchecker.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "endpoint-checker")
public class EndpointCheckerProperties {

    private Duration syncTime;
    private Integer threads;
    private Integer pageSize;

}
