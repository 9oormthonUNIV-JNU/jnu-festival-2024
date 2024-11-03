package com.jnu.festival.domain.partner.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PartnerDto {
    private final Long id;
    private final String name;
    private final String description;
    private final List<String> images;
    private final String location;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Boolean bookmark;
    private final LocalDateTime createdAt;

    @Builder
    public PartnerDto(Long id, String name, String description, List<String> images, String location, LocalDate startDate, LocalDate endDate, Boolean bookmark, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.images = images;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookmark = bookmark;
        this.createdAt = createdAt;
    }
}
