package com.mapofzones.endpointchecker.services.zone;

import com.mapofzones.endpointchecker.domain.Zone;
import com.mapofzones.endpointchecker.services.base.IGenericService;

import java.util.Set;

public interface IZoneService extends IGenericService<Zone, String, ZoneRepository> {

    Set<String> findUniqueZoneNames();

}
