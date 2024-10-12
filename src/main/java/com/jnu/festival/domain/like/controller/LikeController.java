package com.jnu.festival.domain.like.controller;


import com.jnu.festival.domain.like.dto.LikeResponseDTO;
import com.jnu.festival.domain.like.service.LikeService;
import com.jnu.festival.global.security.UserDetailsImpl;
import com.jnu.festival.global.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    //좋아요 등록
    @PostMapping("/api/v1/booths/{boothId}/likes")
    public ResponseEntity<?> postBoothLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boothId) throws Exception {
        likeService.createBoothLike(userDetails, boothId);
        return ResponseEntity.ok(ResponseDto.created(null));
    }

    //좋아요 취소
    @PatchMapping("/api/v1/booths/{boothId}/{likeId}")
    public ResponseEntity<?> updateBoothLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boothId, @PathVariable Long likeId) throws Exception {
        LikeResponseDTO responseDTO = likeService.updateboothlike(userDetails, boothId, likeId);
        return ResponseEntity.ok(ResponseDto.ok(responseDTO));
    }

}
