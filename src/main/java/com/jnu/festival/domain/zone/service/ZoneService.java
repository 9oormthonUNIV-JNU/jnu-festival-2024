package com.jnu.festival.domain.zone.service;

import com.jnu.festival.domain.common.Location;
import com.jnu.festival.domain.zone.dto.response.ZoneListDto;
import com.jnu.festival.domain.zone.entity.Zone;
import com.jnu.festival.domain.zone.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZoneService {
    private final ZoneRepository zoneRepository;

    public List<ZoneListDto> readZoneList(String location){
        List<Zone> zones = zoneRepository.findAllByLocation(Location.from(location));
        return zones.stream()
                .map(zone -> ZoneListDto.builder()
                        .id(zone.getId())
                        .name(zone.getName())
                        .description(zone.getDescription())
                        .build())
                .toList();
    }
}
