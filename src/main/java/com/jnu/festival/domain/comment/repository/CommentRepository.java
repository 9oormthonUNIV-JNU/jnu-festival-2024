package com.jnu.festival.domain.comment.repository;

import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBooth(Booth booth);
}
