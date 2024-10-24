package com.jnu.festival.domain.like.repository;


import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.like.entity.Like;
import com.jnu.festival.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countAllByBoothAndIsDeletedFalse(Booth booth);

    @Query("select like.booth from Like as like where like.user = :user and like.isDeleted = false")
    List<Booth> findAllByUserAndIsDeleted(User user);

    @Query("select like.booth from Like as like where like.user = :user and like.booth = :booth and like.isDeleted = false")
    Optional<Booth> findByUserAndBoothAndIsDeleted(User user, Booth booth);

    @Query("select like from Like as like where like.user = :user and like.booth = :booth")
    Optional<Like> findByUserAndBooth(User user, Booth booth);

    @Query("select like.booth from Like as like join like.booth as booth where like.isDeleted = false group by like.booth order by count(like.booth) desc, like.booth.id asc")
    List<Booth> findTop5ByLike(Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Like as like where like.booth = :booth")
    void deleteAllByBooth(Booth booth);
}
