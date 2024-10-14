package com.jnu.festival.domain.feedback.service;

import com.jnu.festival.domain.common.Location;
import com.jnu.festival.domain.feedback.dto.FeedbackRequestDto;
import com.jnu.festival.domain.feedback.entity.Feedback;
import com.jnu.festival.domain.feedback.entity.FeedbackCategory;
import com.jnu.festival.domain.feedback.entity.FeedbackImage;
import com.jnu.festival.domain.feedback.repository.FeedbackImageRepository;
import com.jnu.festival.domain.feedback.repository.FeedbackRepository;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.config.S3Service;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;
    private final S3Service s3Service;
    private final FeedbackImageRepository feedbackImageRepository;

    @Transactional
    public void createFeedback(FeedbackRequestDto request, MultipartFile image, UserDetailsImpl userDetails) throws IOException {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        Feedback feedback = feedbackRepository.save(
                Feedback.builder()
                        .user(user)
                        .title(request.title())
                        .category(FeedbackCategory.from(request.category()))
                        .content(request.content())
                        .build()
        );

        if (image != null) {
            String url = s3Service.upload(image, "feedback");
            FeedbackImage feedbackImage = FeedbackImage.builder()
                    .feedback(feedback)
                    .url(url)
                    .build();
            feedbackImageRepository.save(feedbackImage);
        }
    }
}
