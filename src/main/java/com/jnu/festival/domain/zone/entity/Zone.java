package com.jnu.festival.domain.zone.entity;

import com.jnu.festival.domain.common.Location;
import com.jnu.festival.global.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "zone")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Zone extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    @Enumerated(EnumType.STRING)
    private Location location;

    @Column(name = "description", nullable = false)
    private String description;

    @Builder
    public Zone(String name, Location location, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
    }
}
