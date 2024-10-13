package com.jnu.festival.domain.timeCapsule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "timecapsule_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TimeCapsuleImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timecapsule_id", nullable = false)
    private TimeCapsule timeCapsule;

    @Column(name = "url", nullable = false)
    private String url;

    @Builder
    public TimeCapsuleImage(TimeCapsule timeCapsule, String url){
        this.timeCapsule = timeCapsule;
        this.url = url;
    }
}
