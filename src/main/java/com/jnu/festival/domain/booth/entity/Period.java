package com.jnu.festival.domain.booth.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Period {
    DAYTIME("daytime"),
    NIGHTTIME("nighttime"),
    ALLTIME("alltime");

    private final String value;

    @JsonCreator
    public static Period from(String value) {
        for (Period period: Period.values()) {
            if (period.value.equals(value)) {
                return period;
            }
        }
        return null;
    }
}
