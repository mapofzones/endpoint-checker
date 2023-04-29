package com.mapofzones.endpointchecker.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfig {

    @Bean
    public DataSource dataSource() {
        return Mockito.mock(DataSource.class);
    }

//    @Bean
//    public EndpointProperties endpointProperties() {
//        return new EndpointProperties();
//    }

}