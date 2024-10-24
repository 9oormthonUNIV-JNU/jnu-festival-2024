package com.jnu.festival.domain.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedbackListDto {
    private final Long id;
    private final String nickname;
    private final String title;
    private final LocalDateTime createdAt;

    @Builder
    public FeedbackListDto(Long id, String nickname, String title, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.createdAt = createdAt;
    }
}
