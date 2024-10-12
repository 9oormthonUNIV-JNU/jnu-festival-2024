package com.jnu.festival.domain.content.entity;

import com.jnu.festival.global.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "content")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Content extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Builder
    public Content(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
