package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChannelStatus {
    @JsonProperty("ID")
    public byte id;
    @JsonProperty("SendQueueCapacity")
    public Integer sendQueueCapacity;
    @JsonProperty("SendQueueSize")
    public Integer sendQueueSize;
    @JsonProperty("Priority")
    public Integer priority;
    @JsonProperty("RecentlySent")
    public Long recentlySent;
}
