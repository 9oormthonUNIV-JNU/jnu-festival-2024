package com.jnu.festival.domain.partner.DTO;

import java.time.LocalDateTime;

public record PartnerSummaryDto(
        Long id,
        String name,
        LocalDateTime createdDate,
        String description
) {
}
