package com.jnu.festival.domain.timecapsule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "timecapsule_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TimecapsuleImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timecapsule_id", nullable = false)
    private Timecapsule timecapsule;

    @Column(name = "url", nullable = false)
    private String url;

    @Builder
    public TimecapsuleImage(Timecapsule timecapsule, String url) {
        this.timecapsule = timecapsule;
        this.url = url;
    }
}
