package com.jnu.festival.domain.like.controller;


import com.jnu.festival.domain.like.dto.response.LikeDto;
import com.jnu.festival.domain.like.service.LikeService;
import com.jnu.festival.global.security.UserDetailsImpl;
import com.jnu.festival.global.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booths")
public class LikeController {
    private final LikeService likeService;

    //좋아요 등록
    @PostMapping("/{boothId}/likes")
    public ResponseEntity<?> createLike(@PathVariable Long boothId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        LikeDto response = likeService.createLike(boothId, userDetails);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }

    //좋아요 취소
    @DeleteMapping("/{boothId}/likes")
    public ResponseEntity<?> deleteLike(@PathVariable Long boothId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        LikeDto response = likeService.deleteLike(boothId, userDetails);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }
}
