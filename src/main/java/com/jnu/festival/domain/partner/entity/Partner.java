package com.jnu.festival.domain.partner.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.jnu.festival.global.util.BaseTimeEntity;
import java.time.LocalDate;

@Entity
@Table(name = "partner")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Partner extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Builder
    public Partner(String name, String location, LocalDate startDate, LocalDate endDate, String description) {
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }
}
