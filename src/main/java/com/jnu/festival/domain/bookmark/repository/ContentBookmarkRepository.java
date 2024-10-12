package com.jnu.festival.domain.bookmark.repository;

import com.jnu.festival.domain.bookmark.entity.ContentBookmark;
import com.jnu.festival.domain.bookmark.entity.PartnerBookmark;
import com.jnu.festival.domain.content.entity.Content;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentBookmarkRepository extends JpaRepository<ContentBookmark, Long> {
    @Query("select cb.content from ContentBookmark as cb where cb.user = :user")
    List<Content> findAllByUser(User user);
}
