package com.mapofzones.endpointchecker.services.node.rpc.client;

import com.mapofzones.endpointchecker.services.node.rpc.client.dto.NodeInfo;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonRpcClientTest {

    @Test
    void initiateRpcClient_ActiveAddress_Test() throws MalformedURLException {
        JsonRpcClient jsonRpcClient = new JsonRpcClient();
        NodeInfo nodeInfo = jsonRpcClient.findNodeInfo("http://65.108.207.214:28657");
        assertAll(() -> {
            assertEquals("cosmoshub-4", nodeInfo.getStatus().getNodeInfo().getNetwork());
            assertEquals("12a4e4ab397ba89ff6a6b2abd1166e504533e768", nodeInfo.getStatus().getNodeInfo().getDefaultNodeID());
        });
    }

    @Test
    void initiateRpcClient_NotActiveAddress_Test() throws MalformedURLException {
        JsonRpcClient jsonRpcClient = new JsonRpcClient();
        NodeInfo nodeInfo = jsonRpcClient.findNodeInfo("http://1.1.1.1:1111");

        assertAll(() -> {
            assertThrows(SocketTimeoutException.class, () -> assertNull(nodeInfo.getStatus()));
            assertThrows(SocketTimeoutException.class, () -> assertNull(nodeInfo.getNetInfo()));
            assertThrows(SocketTimeoutException.class, () -> assertNull(nodeInfo.getABCIInfo()));
        });
    }
}
