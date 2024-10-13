package com.jnu.festival.domain.zone.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Location {
    OVERALL("overall"),
    STAGE("stage"),
    SQUARE_518("518-square"),
    BACKGATE_STREET("backgate-street");

    private final String value;

    @JsonCreator
    public static Location from(String value){
        for (Location location: Location.values()){
            if(location.value.equals(value)){
                return location;
            }
        }
        return null;
    }
}
