package com.jnu.festival.domain.bookmark.controller;

import com.jnu.festival.domain.bookmark.service.ContentBookmarkService;
import com.jnu.festival.domain.bookmark.service.PartnerBookmarkService;
import com.jnu.festival.global.security.UserDetailsImpl;
import com.jnu.festival.global.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks/partners")
public class PartnerBookmarkController {
    private final PartnerBookmarkService partnerBookmarkService;

    @PostMapping(value = "/{partnerId}")
    public ResponseEntity<?> createPartnerBookmark(@PathVariable Long partnerId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        partnerBookmarkService.createPartnerBookmark(partnerId, userDetails);
        return ResponseEntity.ok(ResponseDto.ok(null));
    }

    @DeleteMapping(value = "/{partnerId}")
    public ResponseEntity<?> deletePartnerBookmark(@PathVariable Long partnerId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        partnerBookmarkService.deletePartnerBookmark(partnerId, userDetails);
        return ResponseEntity.ok(ResponseDto.ok(null));
    }
}
