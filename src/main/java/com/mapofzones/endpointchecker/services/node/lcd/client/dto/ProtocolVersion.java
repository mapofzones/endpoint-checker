package com.mapofzones.endpointchecker.services.node.lcd.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProtocolVersion {

    @JsonProperty("p2p")
    private Integer p2p;
    @JsonProperty("block")
    private Integer block;
    @JsonProperty("app")
    private Integer app;

}
