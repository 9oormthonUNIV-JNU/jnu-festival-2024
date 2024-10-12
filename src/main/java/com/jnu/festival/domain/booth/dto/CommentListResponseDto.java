package com.jnu.festival.domain.booth.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentListResponseDto {
    private final List<CommentResponseDto> comments;
    private final int commentCount;

    @Builder
    public CommentListResponseDto(List<CommentResponseDto> comments, int commentCount){
        this.comments = comments;
        this.commentCount = commentCount;
    }
}
