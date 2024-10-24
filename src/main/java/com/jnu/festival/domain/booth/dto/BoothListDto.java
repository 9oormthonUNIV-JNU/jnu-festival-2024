package com.jnu.festival.domain.booth.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class BoothListDto {
    private final Long id;
    private final String name;
    private final String location;
    private final Integer index;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Boolean like;
    private final Long likeCount;

    @Builder
    public BoothListDto(Long id, String name, String location, Integer index,
                        LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime,
                        Boolean like, Long likeCount) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.index = index;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.like = like;
        this.likeCount = likeCount;
    }
}
