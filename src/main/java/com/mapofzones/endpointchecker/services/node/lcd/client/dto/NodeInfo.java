package com.mapofzones.endpointchecker.services.node.lcd.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NodeInfo {

    @JsonProperty("id")
    private String id;
    @JsonProperty("moniker")
    private String moniker;
    @JsonProperty("protocol_version")
    private ProtocolVersion protocolVersion;
    @JsonProperty("network")
    private String network;
    @JsonProperty("channels")
    private String channels;
    @JsonProperty("listen_addr")
    private String listenAddr;
    @JsonProperty("version")
    private String version;
    @JsonProperty("other")
    private Other other;

}
