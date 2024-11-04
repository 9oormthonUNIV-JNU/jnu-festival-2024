package com.jnu.festival.domain.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContentRequestDto(
        @NotBlank(message = "콘텐츠 제목은 필수 입니다.")
        String title,
        @NotNull(message = "콘텐츠 설명은 필수입니다.")
        @Size(max = 1000, message = "콘텐츠 설명은 1000자 이하이어야 합니다.")  // 맥시멈 1000
        String description
) {
}
