package com.jnu.festival.domain.booth.entity;


import com.jnu.festival.global.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "booths")
public class Booth extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Location location;

    @Column
    private Integer index;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private Date startTime;

    @Column
    private Date endTime;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    @Enumerated(EnumType.STRING)
    private Period period;

    @Column
    private String image;

    @Builder
    public Booth(String name, Category category, Date endDate, Period period, Location location, Integer index, Date startDate, Date startTime, Date endTime, String description, String image) {
        this.name = name;
        this.category = category;
        this.location = location;
        this.description = description;
        this.image = image;
        this.endDate = endDate;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.index = index;
        this.period = period;
    }
}
