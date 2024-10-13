package com.jnu.festival.domain.partner.DTO;

import java.time.LocalDate;

public record PartnerDto(
        Long id,
        String name,
        String location,
        LocalDate startDate,
        LocalDate endDate,
        String description
) {
}
