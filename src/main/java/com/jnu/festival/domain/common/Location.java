package com.jnu.festival.domain.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Location {
    STADIUM("stadium"),
    SQUARE_518("square-518"),
    BACKGATE_STREET("backgate-street");

    private final String value;

    @JsonCreator
    public static Location from(String value) {
        for (Location location: Location.values()) {
            if (location.value.equals(value)) {
                return location;
            }
        }
        return null;
    }
}
