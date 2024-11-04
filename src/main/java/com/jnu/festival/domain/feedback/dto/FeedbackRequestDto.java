package com.jnu.festival.domain.feedback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FeedbackRequestDto(
        @NotBlank(message = "피드백 제목은 필수입니다.")
        @Size(max = 30, message = "피드백 제목은 30자 이하이어야 합니다.")  // 맥시멈 30
        String title,

        @NotBlank(message = "피드백 종류는 필수입니다.")
        String category,
        @NotBlank(message = "피드백 내용은 필수입니다.")
        @Size(max = 500, message = "피드백 내용은 500자 이하이어야 합니다.")  // 맥시멈 500
        String content
) {
}
