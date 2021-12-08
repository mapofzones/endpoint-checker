package com.mapofzones.endpointchecker.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.mapofzones.endpointchecker.data.rpc.ABCIInfo;
import com.mapofzones.endpointchecker.data.rpc.NetInfo;
import com.mapofzones.endpointchecker.data.rpc.NodeInfoService;
import com.mapofzones.endpointchecker.data.rpc.Status;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class JsonRpcService {
    private NodeInfoService nodeInfoService;

    public void initiate(String url) throws MalformedURLException {
//        todo: do we need context?
        URL rpc = this.getURLbyUrlString(url);
        JsonRpcHttpClient client = this.createClient(rpc);
        client.setConnectionTimeoutMillis(5000);
        client.setReadTimeoutMillis(5000);
        nodeInfoService = this.getNodeInfoService(client);
    }

    private URL getURLbyUrlString(String url) throws MalformedURLException {
        return new URL(url);
    }

    private JsonRpcHttpClient createClient(URL url) {
        return new JsonRpcHttpClient(url);
    }

    private NodeInfoService getNodeInfoService(JsonRpcHttpClient client) {
        return ProxyUtil.createClientProxy(
                getClass().getClassLoader(),
                NodeInfoService.class,
                client);
    }

    public Status getNodeStatus() {
        return nodeInfoService.getStatus();
    }

    public NetInfo getNetInfo() throws InvalidFormatException {
        return nodeInfoService.getNetInfo();
    }

    public ABCIInfo getABCIInfo() throws SocketTimeoutException, JsonParseException, ConnectException {
        return nodeInfoService.getABCIInfo();
    }
}
