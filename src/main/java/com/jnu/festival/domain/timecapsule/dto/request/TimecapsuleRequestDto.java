package com.jnu.festival.domain.timecapsule.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TimecapsuleRequestDto(
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일을 올바르게 입력해주세요.")
        String mailAddress,
        @Size(max = 500, message = "Timecapsule Content는 500자를 초과할 수 없습니다.")  // 맥시멈 500
        String content,
        Boolean isPublic
) {
}
