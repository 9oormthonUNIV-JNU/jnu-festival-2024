package com.jnu.festival.domain.timecapsule.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TimecapsuleDto {
    private final Long id;
    private final String nickname;
    private final String content;
    private final List<String> images;
    private final LocalDateTime createdAt;

    @Builder
    public TimecapsuleDto(Long id, String nickname, String content, List<String> images, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.images = images;
        this.createdAt = createdAt;
    }
}
