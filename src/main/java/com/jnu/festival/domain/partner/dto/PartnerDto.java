package com.jnu.festival.domain.partner.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PartnerDto {
    private Long id;
    private String name;
    private String description;
    private List<String> images;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean bookmark;
    private LocalDateTime createdAt;

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
