package com.mapofzones.endpointchecker.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;

import static com.mapofzones.endpointchecker.common.constants.CommonConstants.EMPTY_STRING;

@Slf4j
public class URLHelper {

    public static String getPort(String address) {
        try {
            return String.valueOf(new URL(address).getPort());
        } catch (MalformedURLException e) {
            log.warn("Cant find port: " + address);
            return EMPTY_STRING;
        }
    }

    public static boolean isAddressValid(String address) {
        return isIpAddressValid(address) || isDomainNameOfAddressValid(address);
    }

    private static boolean isIpAddressValid(String address) {
        String zeroTo255 = "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])";
        String regex = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;

        String ipAddress = getHost(address);
        if (ipAddress.isBlank())
            return false;
        ;
        return ipAddress.matches(regex);
    }

    private static boolean isDomainNameOfAddressValid(String address) {
        String regex = "[a-z0-9.-]{1,63}";

        String domainName = getHost(address);
        if (domainName.isBlank())
            return false;

        return domainName.matches(regex);
    }

    private static String getHost(String address) {
        try {
            return new URL(address).getHost();
        } catch (MalformedURLException e) {
            log.warn("Cant find host: " + address);
            return EMPTY_STRING;
        }
    }
}
