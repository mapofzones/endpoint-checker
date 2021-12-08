package com.mapofzones.endpointchecker.services;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.mapofzones.endpointchecker.data.rpc.ABCIInfo;
import com.mapofzones.endpointchecker.data.rpc.NetInfo;
import com.mapofzones.endpointchecker.data.rpc.NodeInfoService;
import com.mapofzones.endpointchecker.data.rpc.Status;

import java.net.MalformedURLException;
import java.net.URL;

public class JsonRpcService {
    private NodeInfoService nodeInfoService;

    public void initiate(String url) throws MalformedURLException {
        nodeInfoService = this.getNodeInfoService(
                this.createClient(
                        this.getURLbyUrlString(
                                url)));
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

    public NetInfo getNetInfo() {
        return nodeInfoService.getNetInfo();
    }

    public ABCIInfo getABCIInfo() {
        return nodeInfoService.getABCIInfo();
    }
}
