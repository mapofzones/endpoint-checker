package com.mapofzones.endpointchecker.services.node.location;

import com.mapofzones.endpointchecker.AbstractTest;
import com.mapofzones.endpointchecker.config.LocationClientConf;
import com.mapofzones.endpointchecker.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWebClient
@ContextConfiguration(classes = {LocationClientConf.class, TestConfig.class})
class LocationClientTest extends AbstractTest {

    @Autowired
    private LocationClient locationClient;

    @Test
    void findLocation_Success_Test() {
        LocationDto dto = locationClient.findLocation("43.153.101.118");
        System.out.println();
        assertAll(() -> {
            assertEquals("success", dto.getStatus());
            assertEquals("North America", dto.getContinent());
            assertEquals("NA", dto.getContinentCode());
            assertEquals("United States", dto.getCountry());
            assertEquals("US", dto.getCountryCode());
            assertEquals("America/Los_Angeles", dto.getTimezone());

        });
    }

    @Test
    void findLocation_Fail_Test() {
        LocationDto dto = locationClient.findLocation("0.0.0.0");
        assertEquals("fail", dto.getStatus());
    }

    @Test
    void findLocation_ipOrDnsIsBlank_Test() {
        LocationDto dto = locationClient.findLocation("");
        assertEquals("fail", dto.getStatus());
    }
}