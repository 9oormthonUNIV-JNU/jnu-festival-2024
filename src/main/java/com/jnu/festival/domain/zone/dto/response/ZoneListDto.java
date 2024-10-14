package com.jnu.festival.domain.zone.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ZoneListDto {
    private final Long id;
    private final String name;
    private final String description;

    @Builder
    public ZoneListDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
