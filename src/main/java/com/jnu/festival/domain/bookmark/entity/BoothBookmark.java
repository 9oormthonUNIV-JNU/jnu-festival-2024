package com.jnu.festival.domain.bookmark.entity;

import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booth_bookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoothBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id", nullable = false)
    private Booth booth;

    @Builder
    public BoothBookmark(User user, Booth booth) {
        this.user = user;
        this.booth = booth;
    }
}
