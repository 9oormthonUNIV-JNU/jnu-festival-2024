package com.jnu.festival.domain.bookmark.controller;

import com.jnu.festival.domain.bookmark.service.BookmarkService;
import com.jnu.festival.global.security.UserDetailsImpl;
import com.jnu.festival.global.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping(value = "/contents/{contentId}")
    public ResponseEntity<?> createContentBookmark(@PathVariable Long contentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        bookmarkService.createContentBookmark(contentId, userDetails);
        return ResponseEntity.ok(ResponseDto.ok(null));
    }

    @DeleteMapping(value = "/contents/{contentId}")
    public ResponseEntity<?> deleteContentBookmark(@PathVariable Long contentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        System.out.println(contentId);
        bookmarkService.deleteContentBookmark(contentId, userDetails);
        return ResponseEntity.ok(ResponseDto.ok(null));
    }
}
