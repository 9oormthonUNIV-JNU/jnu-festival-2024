package com.jnu.festival.domain.admin.controller;

import com.jnu.festival.domain.admin.dto.request.BoothRequestDto;
import com.jnu.festival.domain.admin.dto.request.ContentRequestDto;
import com.jnu.festival.domain.admin.dto.request.PartnerRequestDto;
import com.jnu.festival.domain.admin.dto.request.ZoneRequestDto;
import com.jnu.festival.domain.admin.dto.response.FeedbackDto;
import com.jnu.festival.domain.admin.dto.response.FeedbackListDto;
import com.jnu.festival.domain.admin.service.AdminService;
import com.jnu.festival.global.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
public class AdminController {
    private final AdminService adminService;

    @PostMapping(value = "/zones")
    public ResponseEntity<?> createZone(@RequestBody ZoneRequestDto request) throws Exception {
        adminService.createZone(request);
        return ResponseEntity.ok(ResponseDto.created(null));
    }

    @DeleteMapping(value = "/zones/{zoneId}")
    public ResponseEntity<?> deleteZone(@PathVariable Long zoneId) {
        adminService.deleteZone(zoneId);
        return ResponseEntity.ok(ResponseDto.ok(null));
    }

    @PostMapping(value = "/partners", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createPartner(@RequestPart PartnerRequestDto request, @RequestPart(required = false)List<MultipartFile> images) throws Exception {
        adminService.createPartner(request, images);
        return ResponseEntity.ok(ResponseDto.created(null));
    }

    @DeleteMapping(value = "/partners/{partnerId}")
    public ResponseEntity<?> deletePartner(@PathVariable Long partnerId) {
        adminService.deletePartner(partnerId);
        return ResponseEntity.ok(ResponseDto.ok(null));
    }

    @PostMapping(value = "/contents", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createContent(@RequestPart ContentRequestDto request, @RequestPart(required = false)List<MultipartFile> images) throws Exception {
        adminService.createContent(request, images);
        return ResponseEntity.ok(ResponseDto.created(null));
    }

    @DeleteMapping(value = "/contents/{contentId}")
    public ResponseEntity<?> deleteContent(@PathVariable Long contentId) {
        adminService.deleteContent(contentId);
        return ResponseEntity.ok(ResponseDto.ok(null));
    }

    @PostMapping(value = "/booths", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createBooth(@RequestPart BoothRequestDto request, @RequestPart(required = false)List<MultipartFile> images) throws Exception {
        adminService.createBooth(request, images);
        return ResponseEntity.ok(ResponseDto.created(null));
    }

    @DeleteMapping(value = "/booths/{boothId}")
    public ResponseEntity<?> deleteBooth(@PathVariable Long boothId) {
        adminService.deleteBooth(boothId);
        return ResponseEntity.ok(ResponseDto.ok(null));
    }

    @DeleteMapping(value = "/booths/{boothId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long boothId, @PathVariable Long commentId) {
        adminService.deleteComment(boothId, commentId);
        return ResponseEntity.ok(ResponseDto.ok(null));
    }

    @DeleteMapping(value = "/timecapsules/{timecapsuleId}")
    public ResponseEntity<?> deleteTimecapsule(@PathVariable Long timecapsuleId) {
        adminService.deleteTimecapsule(timecapsuleId);
        return ResponseEntity.ok(ResponseDto.ok(null));
    }

    @GetMapping(value = "/feedbacks")
    public ResponseEntity<?> readFeedbackList(@RequestParam(required = false) String category) {
        List<FeedbackListDto> response = adminService.readFeedbackList(category);
        return ResponseEntity.ok((ResponseDto.ok(response)));
    }

    @GetMapping(value = "/feedbacks/{feedbackId}")
    public ResponseEntity<?> readFeedback(@PathVariable Long feedbackId) {
        FeedbackDto response = adminService.readFeedback(feedbackId);
        return ResponseEntity.ok(ResponseDto.ok(response));
    }
}
