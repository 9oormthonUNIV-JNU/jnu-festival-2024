package com.jnu.festival.domain.like.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequestDTO {

    private int userId;
    private int boothId;
    private boolean isDeleted;

}


