package com.mapofzones.endpointchecker.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Parser {
    static String parseChainId(BufferedReader in, String key) throws IOException {
        String chainId = "";
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains("\"" + key + "\": \"")) {
                Pattern pattern = Pattern.compile("\"" + key + "\": \"(.*?)\"");
                Matcher matcher = pattern.matcher(inputLine);
                if (matcher.find()) {
                    chainId = matcher.group(1);
                }
            }
        }
        return chainId;
    }
}
