package com.jnu.festival.domain.booth.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public class BoothDto {
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
    private final Boolean bookmark;
    private final String description;
    private final List<String> images;

    @Builder
    public BoothDto(Long id, String name, String location, Integer index,
                    LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime,
                    Boolean like, Long likeCount, Boolean bookmark,
                    String description, List<String> images) {
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
        this.bookmark = bookmark;
        this.description = description;
        this.images = images;
    }
}
