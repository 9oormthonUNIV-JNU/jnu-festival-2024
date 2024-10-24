package com.jnu.festival.domain.admin.dto.request;

import java.time.LocalDate;

public record PartnerRequestDto(
        String name,
        String location,
        LocalDate startDate,
        LocalDate endDate,
        String description
) {
}
