package com.jnu.festival.domain.booth.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final String name;
    private final String content;
    private final LocalDate created_at;

    @Builder
    public CommentResponseDto(Long id, String name, String content, LocalDate created_at){
        this.id = id;
        this.name = name;
        this.content = content;
        this.created_at = created_at;
    }

}
