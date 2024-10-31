package com.jnu.festival.domain.comment.dto.request;

import jakarta.validation.constraints.Size;

public record CommentRequestDto(
        @Size(max = 500, message = "Comment Content는 500자를 초과할 수 없습니다.")  // 맥시멈 500
        String content
) {
}
