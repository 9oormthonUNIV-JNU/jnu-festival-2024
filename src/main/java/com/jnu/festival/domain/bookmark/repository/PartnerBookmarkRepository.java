package com.jnu.festival.domain.bookmark.repository;

import com.jnu.festival.domain.bookmark.entity.ContentBookmark;
import com.jnu.festival.domain.bookmark.entity.PartnerBookmark;
import com.jnu.festival.domain.content.entity.Content;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PartnerBookmarkRepository extends JpaRepository<PartnerBookmark, Long> {

    @Query("select pb.partner from PartnerBookmark as pb where pb.user = :user and pb.isDeleted = false")
    List<Partner> findAllByUserAndIsDeleted(User user);

    @Query("select pb.partner from PartnerBookmark as pb where pb.user = :user and pb.partner = :partner and pb.isDeleted = false")
    Optional<Partner> findByUserAndPartnerAndIsDeleted(User user, Partner partner);

    @Query("select pb from PartnerBookmark as pb where pb.user = :user and pb.partner = :partner")
    Optional<PartnerBookmark> findByUserAndPartner(User user, Partner partner);
}
