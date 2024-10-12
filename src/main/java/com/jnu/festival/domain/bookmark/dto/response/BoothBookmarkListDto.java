package com.jnu.festival.domain.bookmark.dto.response;

import com.jnu.festival.domain.common.Location;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class BoothBookmarkListDto {
    private final Long id;
    private final String name;

    private final String location;
    private final Integer index;
    private final LocalDate startDate;

    private final LocalDate endDate;

    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Boolean bookmark;

    @Builder
    public BoothBookmarkListDto(Long id, String name, Location location, Integer index,
                                  LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime,
                                  Boolean bookmark) {
        this.id = id;
        this.name = name;
        this.location = location.getValue();
        this.index = index;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookmark = bookmark;
    }
}
