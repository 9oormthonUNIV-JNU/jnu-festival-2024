package com.jnu.festival.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CommentDto {
    private final Long id;
    private final String nickname;
    private final String content;
    private final LocalDateTime createdAt;

    @Builder
    public CommentDto(Long id, String nickname, String content, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
    }
}
