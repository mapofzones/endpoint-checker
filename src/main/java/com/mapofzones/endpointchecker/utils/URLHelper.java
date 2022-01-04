package com.mapofzones.endpointchecker.utils;

import com.mapofzones.endpointchecker.common.exceptions.EndpointCheckerException;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class URLHelper {

    public static String getPort(String address) {
        try {
            return String.valueOf(new URL(address).getPort());
        } catch (MalformedURLException e) {
            log.error("Cant parse address: " + address);
            throw new EndpointCheckerException("Cant parse address: " + address, e.getCause());
        }
    }
}
