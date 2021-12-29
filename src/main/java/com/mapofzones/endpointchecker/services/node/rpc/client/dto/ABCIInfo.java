package com.mapofzones.endpointchecker.services.node.rpc.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ABCIInfo {
    @JsonProperty("response")
    private ResponseInfo response;

    public ResponseInfo getResponse() {
        return response;
    }
}
