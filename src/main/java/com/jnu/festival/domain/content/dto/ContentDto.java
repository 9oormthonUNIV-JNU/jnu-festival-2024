package com.jnu.festival.domain.content.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ContentDto {
    private final Long id;
    private final String title;
    private final String description;
    private final List<String> images;
    private final Boolean bookmark;
    private final LocalDateTime createdAt;

    @Builder
    public ContentDto(Long id, String title, String description, List<String> images, Boolean bookmark, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.images = images;
        this.bookmark = bookmark;
        this.createdAt = createdAt;
    }
}
