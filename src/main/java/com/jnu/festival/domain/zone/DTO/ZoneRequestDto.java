package com.jnu.festival.domain.zone.DTO;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jnu.festival.domain.zone.Entity.Location;
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ZoneRequestDto(
        Long id,
        String title,
        Location location,
        String description
) {
}
