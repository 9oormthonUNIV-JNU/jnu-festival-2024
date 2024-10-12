package com.jnu.festival.domain.like.controller;

import com.jnu.festival.domain.booth.dto.BoothResponseDTO;
import com.jnu.festival.domain.like.dto.LikeRequestDTO;
import com.jnu.festival.domain.like.dto.LikeResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class LikeController {


    //likeService.postBoothLike(boothId);
    //좋아요 등록
/*    @PostMapping("/api/v1/booths/{boothId}/likes")
    public ResponseEntity<?> postBoothLike(@PathVariable int boothId) {
        LikeRequestDTO requestDTO = likeService.createBoothLike(boothId);
        return ResponseEntity.status(HttpStatus.OK).body(requestDTO);
        //return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTO));
    }

    //좋아요 취소
    @PatchMapping("/api/v1/booths/{boothId}/{likeId}")
    public ResponseEntity<?> updateBoothLike(@PathVariable int boothId, @PathVariable int likeId) {
        LikeResponseDTO responseDTO = likeService.updateboothlike(boothId, likeId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        //return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTO));
    }*/

}
