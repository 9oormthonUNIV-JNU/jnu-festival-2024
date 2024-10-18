package com.jnu.festival.domain.bookmark.controller;


import com.jnu.festival.domain.bookmark.dto.response.BoothBookmarkResponseDTO;
import com.jnu.festival.domain.bookmark.service.BoothBookmarkService;
import com.jnu.festival.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.jnu.festival.global.util.ResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks/booths")
public class BoothBookmarkController {
    private final BoothBookmarkService boothBookmarkService;

    //부스 즐겨찾기
    @PostMapping(value = "/{boothId}")
    public ResponseEntity<?> createBoothBookmark(@PathVariable Long boothId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        boothBookmarkService.createBoothBookmark(boothId, userDetails);
        return ResponseEntity.ok(ResponseDto.created(null));
    }

    //부스 즐겨찾기 취소 부스 즐겨찾기 해당 사람, 부스 아이디를 가진 사람
    @DeleteMapping("/{boothId}")
    public ResponseEntity<?> deleteBoothBookmark(@PathVariable Long boothId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        boothBookmarkService.deleteBoothBookmark(boothId, userDetails);
        return ResponseEntity.ok(ResponseDto.ok(null));
    }
}
