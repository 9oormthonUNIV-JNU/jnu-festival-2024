package com.jnu.festival.domain.timeCapsule.entity;

import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.global.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "timecapsule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TimeCapsule extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "mail_address", nullable = false)
    private String mailAddress;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    @Builder
    public TimeCapsule(User user, String mailAddress, String content, boolean isPublic){
        this.user = user;
        this.mailAddress =  mailAddress;
        this.content = content;
        this.isPublic = isPublic;
    }
}
