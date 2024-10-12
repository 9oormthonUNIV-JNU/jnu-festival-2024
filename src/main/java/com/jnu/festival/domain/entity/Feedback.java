package com.jnu.festival.domain.entity;

import com.jnu.festival.global.util.BaseTimeEntity;
import com.jnu.festival.domain.booth.entity.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "feedbacks")
public class Feedback extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private String title;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    private String content;

    @Column(name = "image")
    private String image;

    @Builder
    public Feedback(User user, String title, Category category, String content, String image) {
        this.user = user;
        this.title = title;
        this.category = category;
        this.content = content;
        this.image = image;
    }
}






