package com.jnu.festival.domain.user.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {
    private final String nickname;

    @Builder
    public UserDto(String nickname) {
        this.nickname = nickname;
    }
}
