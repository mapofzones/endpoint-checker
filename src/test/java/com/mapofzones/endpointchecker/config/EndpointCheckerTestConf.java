package com.mapofzones.endpointchecker.config;

import com.mapofzones.endpointchecker.common.properties.EndpointCheckerProperties;
import com.mapofzones.endpointchecker.common.properties.EndpointProperties;
import com.mapofzones.endpointchecker.common.threads.IThreadStarter;
import com.mapofzones.endpointchecker.common.threads.ThreadStarter;
import com.mapofzones.endpointchecker.services.node.rpc.IRpcService;
import com.mapofzones.endpointchecker.services.node.rpc.RpcService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class EndpointCheckerTestConf {

    @Bean
    public EndpointCheckerProperties endpointCheckerProperties() {
        return new EndpointCheckerProperties();
    }

    @Bean
    public EndpointProperties endpointProperties() {
        return new EndpointProperties();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public IThreadStarter nodeCheckerThreadStarter(EndpointCheckerProperties endpointCheckerProperties) {
        return new ThreadStarter(endpointCheckerProperties.getThreads());
    }

    @Bean
    public IRpcService rpcService() {
        return new RpcService();
    }
}
