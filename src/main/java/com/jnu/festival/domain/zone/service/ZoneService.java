package com.jnu.festival.domain.zone.Service;

import com.jnu.festival.domain.partner.Repository.PartnerJPARepository;
import com.jnu.festival.domain.zone.DTO.ZoneRequestDto;
import com.jnu.festival.domain.zone.Entity.Location;
import com.jnu.festival.domain.zone.Entity.Zone;
import com.jnu.festival.domain.zone.Repository.ZoneRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZoneService {
    private final ZoneRepository zoneRepository;

    @Autowired
    public ZoneService(ZoneRepository zoneRepository){
        this.zoneRepository = zoneRepository;
    }

    //zone 조회

    public List<ZoneRequestDto> getZoneByQuery(Location location){
        List<Zone> zones = zoneRepository.findByLocation(location);
        return zones.stream()
                .map(Zone -> new ZoneRequestDto(Zone.getId(),Zone.getTitle(),Zone.getLocation(),Zone.getDescription()))
                .collect(Collectors.toList());
    }
}
