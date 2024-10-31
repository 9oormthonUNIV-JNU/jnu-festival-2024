package com.jnu.festival.domain.admin.dto.request;

import jakarta.validation.constraints.Size;

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
        @Size(max = 1000, message = "Booth Description은 1000자를 초과할 수 없습니다.")  // 맥시멈 1000
        String description,
        String category,
        String period
) {
}
