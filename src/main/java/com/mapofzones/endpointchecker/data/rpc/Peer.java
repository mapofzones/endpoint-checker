package com.mapofzones.endpointchecker.data.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Peer {
    @JsonProperty("node_info")
    public DefaultNodeInfo nodeInfo;
    @JsonProperty("is_outbound")
    public Boolean isOutbound;
    @JsonProperty("connection_status")
    public ConnectionStatus connectionStatus;
    @JsonProperty("remote_ip")
    public String remoteIP;
}
