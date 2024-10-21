package com.jnu.festival.domain.feedback.repository;

import com.jnu.festival.domain.feedback.entity.Feedback;
import com.jnu.festival.domain.feedback.entity.FeedbackImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackImageRepository extends JpaRepository<FeedbackImage, Long> {
    List<FeedbackImage> findAllByFeedback(Feedback feedback);
}
