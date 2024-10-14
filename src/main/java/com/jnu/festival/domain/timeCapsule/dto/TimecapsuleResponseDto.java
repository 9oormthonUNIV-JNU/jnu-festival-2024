package com.jnu.festival.domain.timeCapsule.dto;

import com.jnu.festival.domain.timeCapsule.entity.TimeCapsuleImage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class TimecapsuleResponseDto {
    private final Long id;
    private final String nickname;
    private final String content;
    private final List<String> imageUrls;
    private final LocalDate created_at;

    @Builder
    public TimecapsuleResponseDto(Long id, String nickname, String content, List<String> imageUrls, LocalDate created_at){
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.imageUrls = imageUrls;
        this.created_at = created_at;
    }
}
