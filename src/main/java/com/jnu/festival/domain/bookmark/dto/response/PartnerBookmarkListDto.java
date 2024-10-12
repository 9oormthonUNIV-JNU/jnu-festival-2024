package com.jnu.festival.domain.bookmark.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PartnerBookmarkListDto {
    private final Long id;
    private final String name;
    private final String description;
    private final Boolean bookmark;
    private final LocalDateTime createdAt;

    @Builder
    public PartnerBookmarkListDto(Long id, String name, String description, Boolean bookmark, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bookmark = bookmark;
        this.createdAt = createdAt;
    }
}
