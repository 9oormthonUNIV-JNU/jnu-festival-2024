package com.jnu.festival.domain.zone.Entity;

import com.jnu.festival.domain.partner.entity.BaseTimeEntity;
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

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "location", nullable = false)
    @Enumerated(EnumType.STRING)
    private Location location;

    @Column(name = "description", nullable = true)
    private String description;

    @Builder
    public Zone(String title,Location location,String description){
        this.title = title;
        this.location = location;
        this.description = description;
    }


}
