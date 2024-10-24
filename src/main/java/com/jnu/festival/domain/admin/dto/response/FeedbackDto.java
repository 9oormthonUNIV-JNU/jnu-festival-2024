package com.jnu.festival.domain.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class FeedbackDto {
    private final Long id;
    private final String nickname;
    private final String title;
    private final String content;
    private final List<String> images;
    private final LocalDateTime createdAt;

    @Builder
    public FeedbackDto(Long id, String nickname, String title, String content, List<String> images, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.images = images;
        this.createdAt = createdAt;
    }
}
