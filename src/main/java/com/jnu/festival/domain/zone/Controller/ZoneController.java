package com.jnu.festival.domain.zone.Controller;

import com.jnu.festival.domain.zone.DTO.ZoneRequestDto;
import com.jnu.festival.domain.zone.Entity.Location;
import com.jnu.festival.domain.zone.Repository.ZoneRepository;
import com.jnu.festival.domain.zone.Service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/zones")
public class ZoneController {

    private final ZoneService zoneService;

//    public ZoneController(ZoneService zoneService){
//        this.zoneService = zoneService;
//    }

    @GetMapping
    public ResponseEntity<List<ZoneRequestDto>> getZoneByQuery(@RequestParam Location location){
        List<ZoneRequestDto> result = zoneService.getZoneByQuery(location);
        return ResponseEntity.ok(result);
    }

}
