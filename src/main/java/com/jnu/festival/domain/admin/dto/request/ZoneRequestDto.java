package com.jnu.festival.domain.admin.dto.request;

import jakarta.validation.constraints.Size;

public record ZoneRequestDto(
        String name,
        String location,
        @Size(max = 1000, message = "Zone Description은 1000자를 초과할 수 없습니다.")  // 맥시멈 1000
        String description
) {
}
