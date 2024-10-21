package com.jnu.festival.domain.content.repository;

import com.jnu.festival.domain.content.entity.Content;
import com.jnu.festival.domain.content.entity.ContentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentImageRepository extends JpaRepository<ContentImage, Long> {
    @Query("select ci from ContentImage as ci where ci.content = :content")
    List<ContentImage> findAllByContent(Content content);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from ContentImage as ci where ci.content = :content")
    void deleteAllByContent(Content content);
}
