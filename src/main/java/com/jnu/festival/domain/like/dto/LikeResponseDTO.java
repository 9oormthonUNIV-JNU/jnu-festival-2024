package com.jnu.festival.domain.like.dto;


import com.jnu.festival.domain.like.entity.Like;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeResponseDTO {

    private Long userId;
    private Long boothId;
    private boolean isDeleted;

    public LikeResponseDTO(Like like) {
        this.userId = like.getUser().getId();
        this.boothId = like.getBooth().getId();
        this.isDeleted = like.isDeleted();
    }
}
