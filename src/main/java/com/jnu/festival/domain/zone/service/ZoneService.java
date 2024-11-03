package com.jnu.festival.domain.zone.service;

import com.jnu.festival.global.common.Location;
import com.jnu.festival.domain.zone.dto.response.ZoneListDto;
import com.jnu.festival.domain.zone.entity.Zone;
import com.jnu.festival.domain.zone.repository.ZoneRepository;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneService {
    private final ZoneRepository zoneRepository;

    public List<ZoneListDto> readZoneList(String location) {
        Location convertLocation = Location.from(location);

        if (convertLocation == null) {
            throw new BusinessException(ErrorCode.INVALID_LOCATION);
        }

        List<Zone> zones = zoneRepository.findAllByLocation(convertLocation);
        return zones.stream()
                .map(zone -> ZoneListDto.builder()
                        .id(zone.getId())
                        .name(zone.getName())
                        .description(zone.getDescription())
                        .build())
                .toList();
    }
}
