package com.jnu.festival.domain.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ZoneRequestDto(
        @NotBlank(message = "존 이름은 필수입니다.")
        String name,
        @NotBlank(message = "존 위치는 필수입니다.")
        String location,
        @NotNull(message = "존 설명은 필수입니다.")
        @Size(max = 1000, message = "존 설명은 1000자 이하이어야 합니다.")  // 맥시멈 1000
        String description
) {
}
