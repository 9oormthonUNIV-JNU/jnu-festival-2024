package com.jnu.festival.domain.content.entity;

import com.jnu.festival.global.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "content_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ContentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(name = "url", nullable = false)
    private String url;

    @Builder
    public ContentImage(Content content, String url) {
        this.content = content;
        this.url = url;
    }
}
