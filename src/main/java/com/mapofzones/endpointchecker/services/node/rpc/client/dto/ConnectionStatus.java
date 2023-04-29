package com.mapofzones.endpointchecker.services.node.rpc.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectionStatus {
    @JsonProperty("Duration")
    private Long duration;
    @JsonProperty("SendMonitor")
    private MonitorStatus sendMonitor;
    @JsonProperty("RecvMonitor")
    private MonitorStatus recvMonitor;
    @JsonProperty("Channels")
    private ArrayList<ChannelStatus> channels;

    public Long getDuration() {
        return duration;
    }

    public MonitorStatus getSendMonitor() {
        return sendMonitor;
    }

    public MonitorStatus getRecvMonitor() {
        return recvMonitor;
    }

    public ArrayList<ChannelStatus> getChannels() {
        return channels;
    }
}
