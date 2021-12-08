package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ABCIInfo {
    @JsonProperty("response")
    private ResponseInfo response;

    public ResponseInfo getResponse() {
        return response;
    }
}
