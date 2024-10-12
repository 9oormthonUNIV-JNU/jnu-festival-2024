package com.jnu.festival.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("user"),
    ADMIN("admin");
    private final String role;
}
