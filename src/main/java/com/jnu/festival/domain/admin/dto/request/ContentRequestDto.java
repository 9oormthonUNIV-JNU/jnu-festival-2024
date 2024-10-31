package com.jnu.festival.domain.admin.dto.request;

import jakarta.validation.constraints.Size;

public record ContentRequestDto(
        String title,
        @Size(max = 1000, message = "Content Description은 1000자를 초과할 수 없습니다.")  // 맥시멈 1000
        String description
) {
}
