package com.jnu.festival.domain.booth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoothSearchListDto {
    private final Long id;
    private final String name;

    @Builder
    public BoothSearchListDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
