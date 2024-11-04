package com.jnu.festival.domain.user.repository;

import com.jnu.festival.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);

    @Query("select user.accessToken from User as user where user.nickname = :nickname")
    Optional<String> findAccessTokenByNickname(String nickname);

    @Query("select user.role from User as user where user.accessToken = :accessToken")
    Optional<String> findRoleByAccessToken(String accessToken);
}
