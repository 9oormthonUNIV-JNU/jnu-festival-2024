package com.jnu.festival.domain.timeCapsule.controller;

import com.jnu.festival.domain.timeCapsule.dto.TimeCapsuleRequestDto;
import com.jnu.festival.domain.timeCapsule.dto.TimecapsuleListResponseDto;
import com.jnu.festival.domain.timeCapsule.dto.TimecapsuleResponseDto;
import com.jnu.festival.domain.timeCapsule.service.TimeCapsuleService;
import com.jnu.festival.global.security.UserDetailsImpl;
import com.jnu.festival.global.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/timecapsules")
public class TimeCapsuleController {
    private final TimeCapsuleService timeCapsuleService;

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createTimeCapsule(@RequestPart TimeCapsuleRequestDto request, @RequestPart List<MultipartFile> images, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{
        timeCapsuleService.createTimeCapsule(request, images, userDetails);

        return ResponseEntity.ok().body(ResponseDto.created(null));
    }

    @DeleteMapping(value = "/{timecapsuleId}")
    public ResponseEntity<?> deleteTimeCapsule(@PathVariable Long timecapsuleId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        timeCapsuleService.deleteTimeCapsule(timecapsuleId, userDetails);

        return ResponseEntity.ok().body(ResponseDto.created(null));
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getTimecapsules(@AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception{

        List<TimecapsuleResponseDto> my_timecapsules = timeCapsuleService.getMytimecapsules(userDetails);
        List<TimecapsuleResponseDto> timecapsules = timeCapsuleService.getTimecapsules(userDetails);
        TimecapsuleListResponseDto response = timeCapsuleService.mapResponses(my_timecapsules, timecapsules);

        return ResponseEntity.ok().body(ResponseDto.ok(response));
    }


}
