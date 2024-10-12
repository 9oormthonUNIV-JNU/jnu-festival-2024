package com.jnu.festival.domain.bookmark.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ContentBookmarkListDto {
    private final Long id;
    private final String title;
    private final String description;
    private final Boolean bookmark;
    private final LocalDateTime createdAt;

    @Builder
    public ContentBookmarkListDto(Long id, String title, String description, Boolean bookmark, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.bookmark = bookmark;
        this.createdAt = createdAt;
    }
}
