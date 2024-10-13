package com.jnu.festival.domain.content.controller;

import com.jnu.festival.domain.content.dto.ContentDto;
import com.jnu.festival.domain.content.dto.ContentListDto;
import com.jnu.festival.domain.content.service.ContentService;
import com.jnu.festival.global.security.UserDetailsImpl;
import com.jnu.festival.global.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contents")
public class ContentController {
    private final ContentService contentService;

    @GetMapping(value = "")
    public ResponseEntity<?> readContentList(@AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        List<ContentListDto> response = contentService.readContentList(userDetails);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }

    @GetMapping(value = "/{contentId}")
    public ResponseEntity<?> readContent(@PathVariable Long contentId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        ContentDto response = contentService.readContent(contentId, userDetails);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }
}
