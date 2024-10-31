package com.jnu.festival.domain.timecapsule.dto.request;

import jakarta.validation.constraints.Size;

public record TimecapsuleRequestDto(
        String mailAddress,
        @Size(max = 500, message = "Timecapsule Content는 500자를 초과할 수 없습니다.")  // 맥시멈 500
        String content,
        Boolean isPublic
) {
}
