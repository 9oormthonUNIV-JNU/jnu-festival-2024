package com.jnu.festival.domain.bookmark.dto.response;


import com.jnu.festival.domain.bookmark.entity.BoothBookmark;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoothBookmarkResponseDTO {

    private Long userId;
    private Long boothId;
    private boolean isDeleted;

    public BoothBookmarkResponseDTO(BoothBookmark boothBookmark) {
        this.userId = this.getUserId();
        this.boothId = this.getBoothId();
        this.isDeleted = this.isDeleted();
    }
}
