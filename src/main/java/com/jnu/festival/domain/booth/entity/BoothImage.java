package com.jnu.festival.domain.booth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booth_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoothImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id", nullable = false)
    private Booth booth;

    @Column(name = "url", nullable = false)
    private String url;

    @Builder
    public BoothImage(Booth booth, String url) {
        this.booth = booth;
        this.url = url;
    }
}
