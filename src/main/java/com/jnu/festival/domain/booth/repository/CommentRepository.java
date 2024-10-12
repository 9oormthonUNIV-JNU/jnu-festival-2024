package com.jnu.festival.domain.booth.repository;

import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByBooth(Booth booth);
}
