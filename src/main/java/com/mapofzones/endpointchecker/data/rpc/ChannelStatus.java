package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChannelStatus {
    @JsonProperty("ID")
    private byte id;
    @JsonProperty("SendQueueCapacity")
    private Integer sendQueueCapacity;
    @JsonProperty("SendQueueSize")
    private Integer sendQueueSize;
    @JsonProperty("Priority")
    private Integer priority;
    @JsonProperty("RecentlySent")
    private Long recentlySent;

    public byte getId() {
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
