package com.mapofzones.endpointchecker.services.zone;

import com.mapofzones.endpointchecker.common.constants.QueryConstants;
import com.mapofzones.endpointchecker.domain.Zone;
import com.mapofzones.endpointchecker.services.base.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ZoneRepository extends GenericRepository<Zone, String> {

    @Query(QueryConstants.ZoneQueries.FIND_UNIQUE_ZONE_NAMES)
    Set<String> findUniqueZoneNames();

}
