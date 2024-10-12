package com.jnu.festival.domain.booth.service;


import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.dto.BoothResponseDTO;
import com.jnu.festival.domain.booth.repository.BoothJPARepository;
import com.jnu.festival.global.S3Service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class BoothService {
    private final S3Service s3Service;

    private final BoothJPARepository boothJPARepository;

    //카테고리별 부스 목록 조회
    public List<BoothResponseDTO.BoothListDTO> getBoothList(String location, String period, String category) {
        // JPA Repository 메서드를 호출하여 필터링된 부스 목록을 가져옴
        List<Booth> booths = boothJPARepository.findBooths(location, period, category);

        // 결과를 BoothListDTO로 변환
        return booths.stream()
                .map(BoothResponseDTO.BoothListDTO::from)
                .collect(Collectors.toList());

    }

    // 부스 상세 조회
    public BoothResponseDTO.BoothDetailDTO getBoothDetail(int id) {
        Booth booth = boothJPARepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booth not found with id: " + id));
        return new BoothResponseDTO.BoothDetailDTO(booth);
    }
}
