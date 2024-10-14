package com.jnu.festival.domain.timecapsule.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TimecapsuleListDto {
    private final List<TimecapsuleDto> myTimecapsules;
    private final List<TimecapsuleDto> timecapsules;

    @Builder
    public TimecapsuleListDto(List<TimecapsuleDto> myTimecapsules, List<TimecapsuleDto> timecapsules) {
        this.myTimecapsules = myTimecapsules;
        this.timecapsules = timecapsules;
    }
}
