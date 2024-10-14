package com.jnu.festival.domain.bookmark.controller;

import com.jnu.festival.domain.bookmark.service.ContentBookmarkService;
import com.jnu.festival.global.security.UserDetailsImpl;
import com.jnu.festival.global.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks/contents")
public class ContentBookmarkController {
    private final ContentBookmarkService contentBookmarkService;

    @PostMapping(value = "/{contentId}")
    public ResponseEntity<?> createContentBookmark(@PathVariable Long contentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        contentBookmarkService.createContentBookmark(contentId, userDetails);
        return ResponseEntity.ok(ResponseDto.ok(null));
    }

    @DeleteMapping(value = "/{contentId}")
    public ResponseEntity<?> deleteContentBookmark(@PathVariable Long contentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        contentBookmarkService.deleteContentBookmark(contentId, userDetails);
        return ResponseEntity.ok(ResponseDto.ok(null));
    }
}
