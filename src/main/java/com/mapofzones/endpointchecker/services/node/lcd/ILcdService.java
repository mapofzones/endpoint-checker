package com.mapofzones.endpointchecker.services.node.lcd;

import com.mapofzones.endpointchecker.domain.NodeLcdAddress;

public interface ILcdService {

    void checkLiveness(NodeLcdAddress nodeAddress);

}
