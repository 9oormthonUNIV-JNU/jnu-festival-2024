package com.jnu.festival.domain.like.repository;


import com.jnu.festival.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJPARepository extends JpaRepository<Like, Long> {
}
