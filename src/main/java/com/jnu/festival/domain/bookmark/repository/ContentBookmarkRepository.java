package com.jnu.festival.domain.bookmark.repository;

import com.jnu.festival.domain.bookmark.entity.ContentBookmark;
import com.jnu.festival.domain.bookmark.entity.PartnerBookmark;
import com.jnu.festival.domain.content.entity.Content;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContentBookmarkRepository extends JpaRepository<ContentBookmark, Long> {
    @Query("select cb.content from ContentBookmark as cb where cb.user = :user and cb.isDeleted = false")
    List<Content> findAllByUserAndIsDeleted(User user);

    @Query("select cb.content from ContentBookmark as cb where cb.user = :user and cb.isDeleted = false")
    Content findByUserAndIsDeleted(User user);

    @Query("select cb from ContentBookmark as cb where cb.user = :user and cb.content = :content")
    Optional<ContentBookmark> findByUserAndContent(User user, Content content);
}
