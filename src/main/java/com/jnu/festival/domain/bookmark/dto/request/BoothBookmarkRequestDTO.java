package com.jnu.festival.domain.bookmark.dto.request;


import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoothBookmarkRequestDTO {

    private Long userId;

    private Long boothId;

    private boolean isDeleted;
}
