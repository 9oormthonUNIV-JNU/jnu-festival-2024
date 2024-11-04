package com.jnu.festival.domain.comment.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentRequestDto(
        @NotNull(message = "댓글은 필수입니다.")
        @Size(max = 500, message = "댓글은 500자 이하이어야 합니다.")  // 맥시멈 500
        String content
) {
}
