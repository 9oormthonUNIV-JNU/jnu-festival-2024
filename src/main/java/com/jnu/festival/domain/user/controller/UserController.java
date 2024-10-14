package com.jnu.festival.domain.user.controller;

import com.jnu.festival.domain.bookmark.dto.response.BoothBookmarkListDto;
import com.jnu.festival.domain.bookmark.dto.response.ContentBookmarkListDto;
import com.jnu.festival.domain.bookmark.dto.response.PartnerBookmarkListDto;
import com.jnu.festival.domain.user.dto.response.UserDto;
import com.jnu.festival.domain.user.service.UserService;
import com.jnu.festival.global.util.ResponseDto;
import com.jnu.festival.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping(value = "")
    public ResponseEntity<?> readUser(@AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        UserDto response =  userService.readUser(userDetails);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }

    @GetMapping(value = "/bookmarks/partners")
    public ResponseEntity<?> readPartnerBookmarkList(@AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        List<PartnerBookmarkListDto> response = userService.readPartnerBookmarkList(userDetails);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }

    @GetMapping(value = "/bookmarks/contents")
    public ResponseEntity<?> readContentBookmarkList(@AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        List<ContentBookmarkListDto> response = userService.readContentBookmarkList(userDetails);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }

    @GetMapping(value = "/bookmarks/booths")
    public ResponseEntity<?> readBoothBookmark(@AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        List<BoothBookmarkListDto> response = userService.readBoothBookmark(userDetails);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }
}
