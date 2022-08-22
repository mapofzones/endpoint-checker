package com.mapofzones.endpointchecker.config;

import com.mapofzones.endpointchecker.services.node.rpc.client.JsonRpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcClientConf {

    @Bean
    public JsonRpcClient jsonRpcClient() {
        return new JsonRpcClient();
    }
}
