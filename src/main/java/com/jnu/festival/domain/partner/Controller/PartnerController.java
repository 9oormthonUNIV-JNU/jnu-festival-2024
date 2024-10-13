package com.jnu.festival.domain.partner.Controller;

import com.jnu.festival.domain.partner.DTO.PartnerSummaryDto;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.partner.Service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/partners")
public class PartnerController {
    private final PartnerService partnerService;

    //파트너 목록 조회
    @GetMapping()
    public ResponseEntity<List<PartnerSummaryDto>> getAllPartners(){
        List<PartnerSummaryDto> partners = partnerService.getAllPartners();
        return ResponseEntity.ok(partners);
    }

    //파트너 상세 조회
    @GetMapping("/{partnerId}")
    public ResponseEntity<Partner> getPartnerById(@PathVariable Long id){
        Partner partner = partnerService.getPartnerById(id);
        return ResponseEntity.ok(partner);
    }


}
