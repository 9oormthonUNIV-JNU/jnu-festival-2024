package com.jnu.festival.domain.booth.controller;

import com.jnu.festival.domain.booth.dto.BoothResponseDTO;
import com.jnu.festival.domain.booth.service.BoothService;
import com.jnu.festival.global.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    private final BoothService boothService;
    private final CommentService commentService;

    @GetMapping("/api/v1/booths")
    public ResponseEntity<?> getBoothList(@RequestParam(value = "location") String location,
                                          @RequestParam(value = "period", required = false) String period,
                                          @RequestParam(value = "category", required = false) String category) {
        List<BoothResponseDTO.BoothListDTO> responseDTO = boothService.getBoothList(location, period, category);
        return ResponseEntity.ok(ResponseDto.ok(responseDTO));
    }

    @GetMapping("/api/v1/booths/{boothId}")
    public ResponseEntity<?> getBoothDetail(@PathVariable Long boothId) {
        BoothResponseDTO.BoothDetailDTO responseDTO = boothService.getBoothDetail(boothId);
        return ResponseEntity.ok(ResponseDto.ok(responseDTO));
    }  
  
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
