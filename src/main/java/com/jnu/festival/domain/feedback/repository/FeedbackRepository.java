package com.jnu.festival.domain.feedback.repository;

import com.jnu.festival.domain.feedback.entity.Feedback;
import com.jnu.festival.domain.feedback.entity.FeedbackCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("select f from Feedback as f where (:category is null or f.category = :category)")
    List<Feedback> findAllByCategory(FeedbackCategory category);
}
