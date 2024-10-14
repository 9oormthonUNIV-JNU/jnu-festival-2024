package com.jnu.festival.domain.timecapsule.dto.request;

public record TimecapsuleRequestDto(
        String mailAddress,
        String content,
        boolean isPublic
) {
}
