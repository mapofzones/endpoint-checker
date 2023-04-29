package com.mapofzones.endpointchecker.services.node.rpc.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ABCIInfo {
    @JsonProperty("response")
    private ResponseInfo response;

    public ResponseInfo getResponse() {
        return response;
    }
}
