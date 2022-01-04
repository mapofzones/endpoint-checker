package com.mapofzones.endpointchecker.services.node.lcd.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NodeInfoDto {

    public NodeInfoDto(boolean isSuccessReceived) {
        this.isSuccessReceived = isSuccessReceived;
    }

    @JsonProperty("application_version")
    private ApplicationVersion applicationVersion;

    @JsonProperty("node_info")
    private NodeInfo nodeInfo;

    @JsonIgnore
    private boolean isSuccessReceived;
}
