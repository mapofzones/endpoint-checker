package com.mapofzones.endpointchecker.services.node.lcd;

import com.mapofzones.endpointchecker.domain.Node;

public interface ILcdService {

    void checkLiveness(Node node);

}
