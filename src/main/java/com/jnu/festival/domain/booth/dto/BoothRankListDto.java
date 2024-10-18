package com.jnu.festival.domain.booth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoothRankListDto {
    private final Long id;
    private final String name;
    private final Long likeCount;

    @Builder
    public BoothRankListDto(Long id, String name, Long likeCount) {
        this.id = id;
        this.name = name;
        this.likeCount = likeCount;
    }
}
