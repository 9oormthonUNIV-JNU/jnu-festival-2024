package com.jnu.festival.domain.zone.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ZoneRequestDto(
        Long id,
        String title,
        Location location,
        String description
) {
}
