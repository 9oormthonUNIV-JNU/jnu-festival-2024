package com.jnu.festival.domain.like.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequestDTO {
    private Long userId;
    private Long boothId;
    private boolean isDeleted;

}