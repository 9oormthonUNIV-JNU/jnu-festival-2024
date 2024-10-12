package com.jnu.festival.domain.feedback.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feedback_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FeedbackImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_id", nullable = false)
    private Feedback feedback;

    @Column(name = "url", nullable = false)
    private String url;

    @Builder
    public FeedbackImage(Feedback feedback, String url) {
        this.feedback = feedback;
        this.url = url;
    }
}
