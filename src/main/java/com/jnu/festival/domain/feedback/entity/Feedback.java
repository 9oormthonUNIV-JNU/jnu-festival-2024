package com.jnu.festival.domain.feedback.entity;

import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.global.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Feedback extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private FeedbackCategory category;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Feedback(User user, String title, FeedbackCategory category, String content) {
        this.user = user;
        this.title = title;
        this.category = category;
        this.content = content;
    }
}
