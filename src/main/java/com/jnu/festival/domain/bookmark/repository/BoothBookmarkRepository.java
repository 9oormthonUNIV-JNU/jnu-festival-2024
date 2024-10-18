package com.jnu.festival.domain.bookmark.repository;

import com.jnu.festival.domain.bookmark.entity.BoothBookmark;
import com.jnu.festival.domain.bookmark.entity.PartnerBookmark;
import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoothBookmarkRepository extends JpaRepository<BoothBookmark, Long> {

    @Query("select bb.booth from BoothBookmark as bb where bb.user = :user and bb.isDeleted = false")
    List<Booth> findAllByUserAndIsDeleted(User user);

    @Query("select bb.booth from BoothBookmark as bb where bb.user = :user and bb.booth = :booth and bb.isDeleted = false")
    Optional<Booth> findByUserAndBoothAndIsDeleted(User user, Booth booth);

    @Query("select bb.booth from BoothBookmark as bb where bb.user = :user")
    List<Booth> findAllByUser(User user);

    @Query("select bb from BoothBookmark as bb where bb.user = :user and bb.booth = :booth")
    Optional<BoothBookmark> findByUserAndBooth(User user, Booth booth);
}
