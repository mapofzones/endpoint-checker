package com.mapofzones.endpointchecker.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "location-finder")
public class LocationFinderProperties {

    private Duration syncTime;
    private Duration locationFrequencyCheck;

}
