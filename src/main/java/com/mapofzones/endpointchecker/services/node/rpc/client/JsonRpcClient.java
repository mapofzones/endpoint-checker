package com.mapofzones.endpointchecker.services.node.rpc.client;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.ABCIInfo;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.NetInfo;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.NodeInfo;
import com.mapofzones.endpointchecker.services.node.rpc.client.dto.Status;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class JsonRpcClient {

    private NodeInfo nodeInfo;

    public void initiate(String url) throws MalformedURLException {
//        todo: do we need context?
        URL rpc = this.getURLbyUrlString(url);
        JsonRpcHttpClient client = this.createClient(rpc);
        client.setConnectionTimeoutMillis(5000);
        client.setReadTimeoutMillis(5000);
        nodeInfo = this.getNodeInfo(client);
    }

    private URL getURLbyUrlString(String url) throws MalformedURLException {
        return new URL(url);
    }

    private JsonRpcHttpClient createClient(URL url) {
        return new JsonRpcHttpClient(url);
    }

    private NodeInfo getNodeInfo(JsonRpcHttpClient client) {
        return ProxyUtil.createClientProxy(
                getClass().getClassLoader(),
                NodeInfo.class,
                client);
    }

    public Status getNodeStatus() {
        return nodeInfo.getStatus();
    }

    public NetInfo getNetInfo() throws InvalidFormatException, SocketTimeoutException, ConnectException, JsonParseException {
        return nodeInfo.getNetInfo();
    }

    public ABCIInfo getABCIInfo() throws SocketTimeoutException, JsonParseException, ConnectException {
        return nodeInfo.getABCIInfo();
    }


}
