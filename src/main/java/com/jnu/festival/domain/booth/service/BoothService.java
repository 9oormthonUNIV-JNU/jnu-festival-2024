package com.jnu.festival.domain.booth.service;


import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.dto.BoothResponseDTO;
import com.jnu.festival.domain.booth.repository.BoothJPARepository;
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
    private final BoothJPARepository boothJPARepository;

    //카테고리별 부스 목록 조회
    public List<BoothResponseDTO.BoothListDTO> getBoothList(String location, String period, String category) {

        List<Booth> booths = boothJPARepository.findBooths(location, period, category);

        // 결과를 BoothListDTO로 변환
        return booths.stream()
                .map(BoothResponseDTO.BoothListDTO::from)
                .collect(Collectors.toList());

    }

    // 부스 상세 조회
    public BoothResponseDTO.BoothDetailDTO getBoothDetail(Long id) {
        Booth booth = boothJPARepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booth not found with id: " + id));
        return new BoothResponseDTO.BoothDetailDTO(booth);
    }
}
