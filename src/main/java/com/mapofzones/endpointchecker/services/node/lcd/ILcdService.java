package com.mapofzones.endpointchecker.services.node.lcd;

import com.mapofzones.endpointchecker.domain.NodeLcdAddress;

import java.util.Set;

public interface ILcdService {

    void checkLiveness(NodeLcdAddress nodeAddress, Set<String> zoneNames);

}
