package com.mapofzones.endpointchecker.services.zone;

import com.mapofzones.endpointchecker.domain.Zone;
import com.mapofzones.endpointchecker.services.base.GenericService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class ZoneService extends GenericService<Zone, String, ZoneRepository> implements IZoneService {

    public ZoneService(ZoneRepository repository) {
        super(repository);
    }

    @Override
    public Set<String> findUniqueZoneNames() {
        return repository.findUniqueZoneNames();
    }
}
