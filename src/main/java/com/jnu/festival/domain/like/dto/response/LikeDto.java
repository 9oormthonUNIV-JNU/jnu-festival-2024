package com.jnu.festival.domain.like.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeDto {
    private final Long likeCount;

    @Builder
    public LikeDto(Long likeCount) {
        this.likeCount = likeCount;
    }
}
