package com.jnu.festival.domain.bookmark.repository;

import com.jnu.festival.domain.bookmark.entity.PartnerBookmark;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartnerBookmarkRepository extends JpaRepository<PartnerBookmark, Long> {
    @Query("select pb.partner from PartnerBookmark as pb where pb.user = :user")
    List<Partner> findAllByUser(User user);
}
