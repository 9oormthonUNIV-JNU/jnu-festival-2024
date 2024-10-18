package com.jnu.festival.domain.comment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentListDto {
    private final List<CommentDto> comments;
    private final Integer commentCount;

    @Builder
    public CommentListDto(List<CommentDto> comments, Integer commentCount) {
        this.comments = comments;
        this.commentCount = commentCount;
    }
}
