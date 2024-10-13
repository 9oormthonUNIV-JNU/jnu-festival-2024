package com.jnu.festival.domain.bookmark.controller;


import com.jnu.festival.domain.bookmark.dto.response.BoothBookmarkResponseDTO;
import com.jnu.festival.domain.bookmark.service.BoothBookmarkService;
import com.jnu.festival.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jnu.festival.global.util.ResponseDto;

@RequiredArgsConstructor
@RestController
public class BoothBookmarkController {

    private final BoothBookmarkService boothBookmarkService;


    //부스 즐겨찾기
    @PostMapping("/api/v1/bookmarks/booths/{boothId}")
    public ResponseEntity<?> postBoothBookmark(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boothId) throws Exception {
        boothBookmarkService.createBoothBookmark(userDetails, boothId);
        return ResponseEntity.ok(ResponseDto.created(null));
    }

    //부스 즐겨찾기 취소 부스 즐겨찾기 해당 사람, 부스 아이디를 가진 사람
    @PatchMapping("/api/v1/bookmarks/booths/{boothId}")
    public ResponseEntity<?> deleteBoothBookmark(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boothId) throws Exception {
        BoothBookmarkResponseDTO responseDTO = boothBookmarkService.updateIsDeleted(userDetails, boothId);
        return ResponseEntity.ok(ResponseDto.ok(responseDTO));
    }
}
