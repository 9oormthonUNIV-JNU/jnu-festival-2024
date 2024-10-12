package com.jnu.festival.domain.feedback.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FeedbackRequestDto(
        String title,
        String category,
        String content
) {
}
