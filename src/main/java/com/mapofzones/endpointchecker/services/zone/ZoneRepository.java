package com.mapofzones.endpointchecker.services.zone;

import com.mapofzones.endpointchecker.common.constants.QueryConstants;
import com.mapofzones.endpointchecker.domain.Zone;
import com.mapofzones.endpointchecker.services.base.GenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ZoneRepository extends GenericRepository<Zone, String> {
    @Query(value = QueryConstants.GET_ZONE_NAMES, nativeQuery = true)
    List<Zone> getZoneNames();

    @Query("SELECT DISTINCT(z.chainId) FROM Zone z")
    Set<String> findUniqueZoneNames();
}
