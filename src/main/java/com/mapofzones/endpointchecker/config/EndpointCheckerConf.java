package com.mapofzones.endpointchecker.config;

import com.mapofzones.endpointchecker.common.properties.EndpointCheckerProperties;
import com.mapofzones.endpointchecker.common.properties.EndpointProperties;
import com.mapofzones.endpointchecker.common.properties.LocationFinderProperties;
import com.mapofzones.endpointchecker.common.threads.IThreadStarter;
import com.mapofzones.endpointchecker.common.threads.ThreadStarter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointCheckerConf {

    @Bean
    public EndpointCheckerProperties endpointCheckerProperties() {
        return new EndpointCheckerProperties();
    }

    @Bean
    public EndpointProperties endpointProperties() {
        return new EndpointProperties();
    }

    @Bean
    public LocationFinderProperties locationFinderProperties() {
        return new LocationFinderProperties();
    }

    @Bean
    public IThreadStarter nodeCheckerThreadStarter() {
        return new ThreadStarter(endpointCheckerProperties().getThreads(), endpointCheckerProperties().getThreadsNaming());
    }
}
