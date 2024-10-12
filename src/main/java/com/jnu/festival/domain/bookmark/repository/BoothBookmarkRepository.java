package com.jnu.festival.domain.bookmark.repository;

import com.jnu.festival.domain.bookmark.entity.BoothBookmark;
import com.jnu.festival.domain.bookmark.entity.PartnerBookmark;
import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoothBookmarkRepository extends JpaRepository<BoothBookmark, Long> {
    @Query("select bb.booth from BoothBookmark as bb where bb.user = :user")
    List<Booth> findAllByUser(User user);
}
