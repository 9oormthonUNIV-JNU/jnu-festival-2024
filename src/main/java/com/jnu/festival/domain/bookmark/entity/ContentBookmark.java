package com.jnu.festival.domain.bookmark.entity;

import com.jnu.festival.domain.content.entity.Content;
import com.jnu.festival.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "content_bookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ContentBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Builder
    public ContentBookmark(User user, Content content) {
        this.user = user;
        this.content = content;
    }
}
