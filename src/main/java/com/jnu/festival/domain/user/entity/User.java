package com.jnu.festival.domain.user.entity;

import com.jnu.festival.global.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "access_token")
    private String accessToken;

    @Builder
    public User(String nickname, String password, Role role, String accessToken) {
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.accessToken = accessToken;
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
