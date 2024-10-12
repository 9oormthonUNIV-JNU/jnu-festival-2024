package com.jnu.festival.domain.booth.controller;


import com.jnu.festival.domain.booth.dto.BoothResponseDTO;
import com.jnu.festival.domain.booth.service.BoothService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoothController {

    private final BoothService boothService;


    @GetMapping("/api/v1/booths")
    public ResponseEntity<?> getBoothList(@RequestParam(value = "location") String location,
                                          @RequestParam(value = "period", required = false) String period,
                                          @RequestParam(value = "category", required = false) String category) {
        List<BoothResponseDTO.BoothListDTO> responseDTO = boothService.getBoothList(location, period, category);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        //return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTO));
    }

    @GetMapping("/api/v1/booths/{boothId}")
    public ResponseEntity<?> getBoothDetail(@PathVariable int boothId) {
        BoothResponseDTO.BoothDetailDTO responseDTO = boothService.getBoothDetail(boothId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        //return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseDTO));
    }

}



