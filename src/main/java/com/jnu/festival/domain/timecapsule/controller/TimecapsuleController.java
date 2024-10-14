package com.jnu.festival.domain.timecapsule.controller;

import com.jnu.festival.domain.timecapsule.dto.request.TimecapsuleRequestDto;
import com.jnu.festival.domain.timecapsule.dto.response.TimecapsuleListDto;
import com.jnu.festival.domain.timecapsule.dto.response.TimecapsuleDto;
import com.jnu.festival.domain.timecapsule.service.TimecapsuleService;
import com.jnu.festival.global.security.UserDetailsImpl;
import com.jnu.festival.global.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/timecapsules")
public class TimecapsuleController {
    private final TimecapsuleService timecapsuleService;

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createTimecapsule(@RequestPart TimecapsuleRequestDto request, @RequestPart(required = false) List<MultipartFile> images, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        timecapsuleService.createTimecapsule(request, images, userDetails);
        return ResponseEntity.ok(ResponseDto.created(null));
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getTimecapsuleList(@AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        TimecapsuleListDto response = timecapsuleService.getTimecapsuleList(userDetails);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }

    @DeleteMapping(value = "/{timecapsuleId}")
    public ResponseEntity<?> deleteTimecapsule(@PathVariable Long timecapsuleId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        timecapsuleService.deleteTimecapsule(timecapsuleId, userDetails);
        return ResponseEntity.ok(ResponseDto.created(null));
    }
}
