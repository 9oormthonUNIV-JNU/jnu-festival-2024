package com.jnu.festival.domain.admin.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record BoothRequestDto(
        String name,
        String location,
        Integer index,
        LocalDate startDate,
        LocalDate endDate,
        LocalTime startTime,
        LocalTime endTime,
        String description,
        String category,
        String period
) {
}
