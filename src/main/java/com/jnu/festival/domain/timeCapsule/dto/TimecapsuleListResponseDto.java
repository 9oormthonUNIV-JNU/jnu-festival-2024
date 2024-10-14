package com.jnu.festival.domain.timeCapsule.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TimecapsuleListResponseDto {
    private final List<TimecapsuleResponseDto> my_timecapsules;
    private final List<TimecapsuleResponseDto> timecapsules;

    @Builder
    public TimecapsuleListResponseDto(List<TimecapsuleResponseDto> my_timecapsules, List<TimecapsuleResponseDto> timecapsules){
        this.my_timecapsules = my_timecapsules;
        this.timecapsules = timecapsules;
    }

}
