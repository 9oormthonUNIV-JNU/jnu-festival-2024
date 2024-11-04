package com.jnu.festival.domain.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PartnerRequestDto(
        @NotBlank(message = "제휴업체 이름은 필수입니다.")
        String name,
        @NotBlank(message = "제휴업체 위치는 필수입니다.")
        String location,
        @NotBlank(message = "제휴업체 시작 날짜는 필수입니다.")
        LocalDate startDate,
        @NotBlank(message = "제휴업체 종료 날짜는 필수입니다.")
        LocalDate endDate,
        @NotNull(message = "제휴업체 설명은 필수입니다.")
        @Size(max = 1000, message = "제휴업체 설명은 1000자 이하이어야 합니다.")  // 맥시멈 1000
        String description
) {
}
