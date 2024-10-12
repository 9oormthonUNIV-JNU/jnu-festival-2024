package com.jnu.festival.domain.booth.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Location {
    FIVE_EIGHTEEN_SQUARE("FiveEighteenSquare"),  // 5.18 광장
    BACK_GATE_STREET("BackGateStreet");          // 후문거리

    private final String location;

}
