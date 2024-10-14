package com.jnu.festival.domain.timeCapsule.dto;

public record TimeCapsuleRequestDto(
        String mail_address,
        String content,
        boolean is_public
) {
}
