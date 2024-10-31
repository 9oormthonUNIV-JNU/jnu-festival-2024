package com.jnu.festival.domain.admin.dto.request;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PartnerRequestDto(
        String name,
        String location,
        LocalDate startDate,
        LocalDate endDate,
        @Size(max = 1000, message = "Partner Description은 1000자를 초과할 수 없습니다.")  // 맥시멈 1000
        String description
) {
}
