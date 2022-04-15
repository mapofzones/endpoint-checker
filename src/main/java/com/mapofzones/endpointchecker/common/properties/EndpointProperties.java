package com.mapofzones.endpointchecker.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "endpoint")
public class EndpointProperties {

    private RPC rpc;
    private LCD lcd;
    private String ipInfo;

    @Getter
    @Setter
    public static class RPC {

    }

    @Getter
    @Setter
    public static class LCD {
        private String nodeInfo;
    }
}