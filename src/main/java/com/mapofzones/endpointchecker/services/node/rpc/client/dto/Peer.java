package com.mapofzones.endpointchecker.services.node.rpc.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Peer {
    @JsonProperty("node_info")
    private DefaultNodeInfo nodeInfo;

    @JsonProperty("is_outbound")
    private Boolean isOutbound;

    @JsonProperty("connection_status")
    private ConnectionStatus connectionStatus;

    @JsonProperty("remote_ip")
    private String remoteIP;

    public DefaultNodeInfo getNodeInfo() {
        return nodeInfo;
    }

    public Boolean getOutbound() {
        return isOutbound;
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public String getRemoteIP() {
        return remoteIP;
    }
}
