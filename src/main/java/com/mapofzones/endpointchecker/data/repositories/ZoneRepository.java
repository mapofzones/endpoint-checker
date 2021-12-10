package com.mapofzones.endpointchecker.data.repositories;

import com.mapofzones.endpointchecker.data.constants.QueryConstants;
import com.mapofzones.endpointchecker.data.entities.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, String> {
    @Query(value = QueryConstants.GET_ZONE_NAMES, nativeQuery = true)
    List<Zone> getZoneNames();
}
