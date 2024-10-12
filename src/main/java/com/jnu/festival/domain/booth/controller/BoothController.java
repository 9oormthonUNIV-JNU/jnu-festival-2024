package com.jnu.festival.domain.booth.controller;

import com.jnu.festival.domain.bookmark.dto.response.BoothBookmarkListDto;
import com.jnu.festival.domain.bookmark.dto.response.ContentBookmarkListDto;
import com.jnu.festival.domain.bookmark.dto.response.PartnerBookmarkListDto;
import com.jnu.festival.domain.booth.dto.CommentListResponseDto;
import com.jnu.festival.domain.booth.dto.CommentRequestDto;
import com.jnu.festival.domain.booth.dto.CommentResponseDto;
import com.jnu.festival.domain.booth.service.CommentService;
import com.jnu.festival.domain.user.dto.response.UserDto;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.service.UserService;
import com.jnu.festival.global.util.ResponseDto;
import com.jnu.festival.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booths")
public class BoothController {

    private final CommentService commentService;
    @PostMapping(value = "/{boothId}/comments")
    public ResponseEntity<?> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boothId, @RequestBody CommentRequestDto dto) throws Exception {

        commentService.createComment(userDetails, boothId, dto);
        return ResponseEntity.ok().body(ResponseDto.created(null));
    }

    @DeleteMapping(value = "/{boothId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boothId, @PathVariable Long commentId) throws Exception{

        commentService.deleteComment(userDetails, boothId, commentId);
        return ResponseEntity.ok().body(ResponseDto.created(null));
    }

    @GetMapping(value = "/{boothId}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long boothId){

        CommentListResponseDto response = commentService.getComments(boothId);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }


}