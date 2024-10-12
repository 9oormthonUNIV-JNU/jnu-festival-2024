package com.jnu.festival.domain.booth.entity;

import com.jnu.festival.domain.common.Location;
import com.jnu.festival.global.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "booth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Booth extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private Location location;

    @Column(name = "index", nullable = false)
    private Integer index;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "category", nullable = false)
    private BoothCategory category;

    @Column(name = "period", nullable = false)
    private Period period;

    @Builder
    public Booth(String name, Location location, Integer index,
                 LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime,
                 String description, BoothCategory category, Period period) {
        this.name = name;
        this.location = location;
        this.index = index;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.category = category;
        this.period = period;
    }
}
