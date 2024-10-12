package com.jnu.festival.domain.like.dto;

import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.entity.User;
import com.jnu.festival.domain.like.entity.Like;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeResponseDTO {

    private int userId;
    private int boothId;
    private boolean is_deleted;

    public LikeResponseDTO(Like like) {
        this.userId = like.getUser().getId();
        this.boothId = like.getBooth().getId();
        this.is_deleted = like.is_deleted();
    }
}
