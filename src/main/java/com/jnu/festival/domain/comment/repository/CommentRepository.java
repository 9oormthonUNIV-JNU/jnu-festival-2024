package com.jnu.festival.domain.comment.repository;

import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBooth(Booth booth);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Comment as comment where comment.booth = :booth")
    void deleteAllByBooth(Booth booth);
}
