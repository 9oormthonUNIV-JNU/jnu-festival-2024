package com.jnu.festival.domain.timecapsule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TimecapsuleRequestDto(
        @NotBlank(message = "타임캡슐 이메일은 필수입니다.")
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
        String mailAddress,
        @NotNull(message = "타임캡슐 내용은 필수입니다.")
        @Size(max = 500, message = "타임캡슐 내용은 500자 이하이어야 합니다.")  // 맥시멈 500
        String content,

        @NotNull(message = "타임캡슐 공개 여부는 필수입니다.")
        Boolean isPublic
) {
}
