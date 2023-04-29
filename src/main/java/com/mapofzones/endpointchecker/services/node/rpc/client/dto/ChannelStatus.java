package com.mapofzones.endpointchecker.services.node.rpc.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelStatus {
    @JsonProperty("ID")
    private Integer id;
    @JsonProperty("SendQueueCapacity")
    private Integer sendQueueCapacity;
    @JsonProperty("SendQueueSize")
    private Integer sendQueueSize;
    @JsonProperty("Priority")
    private Integer priority;
    @JsonProperty("RecentlySent")
    private Long recentlySent;

    public Integer getId() {
        return id;
    }

    public Integer getSendQueueCapacity() {
        return sendQueueCapacity;
    }

    public Integer getSendQueueSize() {
        return sendQueueSize;
    }

    public Integer getPriority() {
        return priority;
    }

    public Long getRecentlySent() {
        return recentlySent;
    }
}
