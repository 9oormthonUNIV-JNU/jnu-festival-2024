package com.jnu.festival.domain.booth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    FOOD("Food"),          // 음식
    EXPERIENCE("Experience"),    // 체험
    PROMOTION("Promotion"),     // 홍보
    FLEA_MARKET("FleaMarket"),   // 플리마켓
    OTHER("Other"); // 기타
    private final String category;
}