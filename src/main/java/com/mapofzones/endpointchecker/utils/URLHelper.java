package com.mapofzones.endpointchecker.utils;

import com.mapofzones.endpointchecker.common.exceptions.EndpointCheckerException;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static boolean isIpAddressValid(String address) {
        String ip;
        String zeroTo255 = "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])";
        String regex = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;

        try {
            ip = new URL(address).getHost();
        } catch (MalformedURLException e) {
            log.error("Cant parse address: " + address);
            ip = "";
        }

        if (ip == null || ip.isBlank())
            return false;

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(ip);
        return m.matches();
    }
}
