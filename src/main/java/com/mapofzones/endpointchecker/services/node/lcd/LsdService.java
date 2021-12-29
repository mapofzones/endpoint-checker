package com.mapofzones.endpointchecker.services.node.lcd;

import com.mapofzones.endpointchecker.common.constants.NodeConstants;
import com.mapofzones.endpointchecker.domain.Node;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class LsdService implements ILcdService {

    @Override
    public void checkLiveness(Node node) {
        try {
            String lcd;
            if (node.getLcdAddr() == null || node.getLcdAddr().isEmpty()) {
                String port = Integer.toString(new URL(node.getAddress()).getPort());
                lcd = node.getAddress().replace(":" + port, ":" + NodeConstants.LCD_DEFAULT_PORT);
            } else lcd = node.getLcdAddr();
            URL url = new URL(lcd + "/node_info");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            int status = con.getResponseCode();
            if (status == 200) {
                node.setIsLcdAddrActive(true);
                node.setLcdAddr(lcd);
            }
            else node.setIsLcdAddrActive(false);
            con.disconnect();
        } catch (IOException e) {
            node.setIsLcdAddrActive(false);
        }
    }
}
