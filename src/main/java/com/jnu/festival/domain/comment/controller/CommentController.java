package com.jnu.festival.domain.comment.controller;

import com.jnu.festival.domain.booth.service.BoothService;
import com.jnu.festival.domain.comment.dto.request.CommentRequestDto;
import com.jnu.festival.domain.comment.dto.response.CommentListDto;
import com.jnu.festival.domain.comment.service.CommentService;
import com.jnu.festival.global.security.UserDetailsImpl;
import com.jnu.festival.global.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booths")
public class CommentController {
    private final BoothService boothService;
    private final CommentService commentService;

    @GetMapping(value = "/{boothId}/comments")
    public ResponseEntity<?> readCommentList(@PathVariable Long boothId) {
        CommentListDto response = commentService.readCommentList(boothId);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }

    @PostMapping(value = "/{boothId}/comments")
    public ResponseEntity<?> createComment(@PathVariable Long boothId, @RequestBody CommentRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        commentService.createComment(boothId, request, userDetails);
        return ResponseEntity.ok(ResponseDto.created(null));
    }

    @DeleteMapping(value = "/{boothId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long boothId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        commentService.deleteComment(boothId, commentId, userDetails);
        return ResponseEntity.ok(ResponseDto.created(null));
    }


}
