package com.jnu.festival.domain.booth.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoothCategory {
    FOOD("food"),
    FLEA_MARKET("flea-market"),
    PROMOTION("promotion"),
    EXPERIENCE("experience"),
    ETC("etc");

    private final String value;

    @JsonCreator
    public static BoothCategory from(String value) {
        for (BoothCategory category: BoothCategory.values()) {
            if (category.value.equals(value)) {
                return category;
            }
        }
        return null;
    }
}
