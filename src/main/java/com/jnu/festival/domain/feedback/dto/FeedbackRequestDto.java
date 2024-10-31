package com.jnu.festival.domain.feedback.dto;

import jakarta.validation.constraints.Size;

public record FeedbackRequestDto(
        @Size(max = 30, message = "Feedback title은 30자를 초과할 수 없습니다.")  // 맥시멈 30
        String title,
        String category,

        @Size(max = 500, message = "Feedback Content는 500자를 초과할 수 없습니다.")  // 맥시멈 500
        String content
) {
}
