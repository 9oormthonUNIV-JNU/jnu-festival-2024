package com.jnu.festival.domain.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record BoothRequestDto(
        @NotBlank(message = "부스 이름은 필수입니다.")
        String name,
        @NotBlank(message = "부스 위치는 필수입니다.")
        String location,
        @NotNull(message = "부스 번호는 필수입니다.")
        Integer index,
        @NotNull(message = "부스 시작 날짜는 필수입니다.")
        LocalDate startDate,
        @NotNull(message = "부스 종료 날짜는 필수입니다.")
        LocalDate endDate,
        @NotNull(message = "부스 시작 시간은 필수입니다.")
        LocalTime startTime,
        @NotNull(message = "부스 종료 시간은 필수입니다.")
        LocalTime endTime,
        @NotNull(message = "부스 설명은 필수입니다.")
        @Size(max = 1000, message = "부스 설명은 1000자 이하이어야 합니다.")  // 맥시멈 1000
        String description,
        @NotBlank(message = "부스 종류는 필수입니다.")
        String category,
        @NotBlank(message = "부스 기간은 필수입니다.")
        String period
) {
}
