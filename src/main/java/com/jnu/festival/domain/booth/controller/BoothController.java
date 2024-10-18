package com.jnu.festival.domain.booth.controller;

import com.jnu.festival.domain.booth.dto.BoothDto;
import com.jnu.festival.domain.booth.dto.BoothListDto;
import com.jnu.festival.domain.booth.dto.BoothRankListDto;
import com.jnu.festival.domain.booth.dto.BoothSearchListDto;
import com.jnu.festival.domain.booth.service.BoothService;
import com.jnu.festival.global.security.UserDetailsImpl;
import com.jnu.festival.global.util.ResponseDto;
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

    @GetMapping(value = "")
    public ResponseEntity<?> readBoothList(@RequestParam(value = "location", required = false) String location,
                                           @RequestParam(value = "period", required = false) String period,
                                           @RequestParam(value = "category", required = false) String category,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        List<BoothListDto> response = boothService.readBoothList(location, period, category, userDetails);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }

    @GetMapping(value = "/{boothId}")
    public ResponseEntity<?> readBooth(@PathVariable Long boothId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        BoothDto response = boothService.readBooth(boothId, userDetails);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> readBoothSearchList(@RequestParam String keyword) {
        List<BoothSearchListDto> response = boothService.readBoothSearchList(keyword);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }

    @GetMapping(value = "/ranks")
    public ResponseEntity<?> readBoothRankList() {
        List<BoothRankListDto> response = boothService.readBoothRankList();
        return ResponseEntity.ok(ResponseDto.ok(response));
    }
}
