package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ConnectionStatus {
    @JsonProperty("Duration")
    public Long duration;
    @JsonProperty("SendMonitor")
    public MonitorStatus sendMonitor;
    @JsonProperty("RecvMonitor")
    public MonitorStatus recvMonitor;
    @JsonProperty("Channels")
    public ArrayList<ChannelStatus> channels;
}
