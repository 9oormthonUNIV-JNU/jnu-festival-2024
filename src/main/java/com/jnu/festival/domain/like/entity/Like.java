package com.jnu.festival.domain.like.entity;


import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.global.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "likes")
public class Like extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  //(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne  //(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id", nullable = false)
    private Booth booth;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @Builder
    public Like(User user, Booth booth, boolean isDeleted) {
        this.user = user;
        this.booth = booth;
        this.isDeleted = isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
