package com.jnu.festival.domain.feedback.dto;

public record FeedbackRequestDto(
        String title,
        String category,
        String content
) {
}
