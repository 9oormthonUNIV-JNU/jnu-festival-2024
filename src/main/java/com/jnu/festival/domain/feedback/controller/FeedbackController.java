package com.jnu.festival.domain.feedback.controller;

import com.jnu.festival.domain.feedback.dto.FeedbackRequestDto;
import com.jnu.festival.domain.feedback.service.FeedbackService;
import com.jnu.festival.global.util.ResponseDto;
import com.jnu.festival.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feedbacks")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createFeedback(@RequestPart FeedbackRequestDto request, @RequestPart List<MultipartFile> images, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        feedbackService.createFeedback(request, images, userDetails);
        return ResponseEntity.ok().body(ResponseDto.created(null));
    }

}
