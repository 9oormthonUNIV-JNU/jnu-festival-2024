package com.jnu.festival.domain.partner.dto;

import java.time.LocalDateTime;

public record PartnerSummaryDto(
        Long id,
        String name,
        LocalDateTime createdDate,
        String description
) {
}
