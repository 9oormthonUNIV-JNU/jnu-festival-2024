package com.jnu.festival.domain.partner.Service;

import com.jnu.festival.domain.bookmark.entity.PartnerBookmark;
import com.jnu.festival.domain.bookmark.repository.PartnerBookmarkRepository;
import com.jnu.festival.domain.partner.DTO.PartnerSummaryDto;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.partner.Repository.PartnerImageRepository;
import com.jnu.festival.domain.partner.Repository.PartnerJPARepository;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.exceptions.ResourceNotFoundException;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerJPARepository partnerJPARepository;
    private final PartnerImageRepository partnerImageRepository;
    private final UserRepository userRepository;
    private final PartnerBookmarkRepository partnerBookmarkRepository;


//    @Autowired
//    public PartnerService(PartnerJPARepository partnerJPARepository){
//        this.partnerJPARepository = partnerJPARepository;
//    }
    //파트너 전체 조회
    public List<PartnerSummaryDto> getAllPartners(){
        List<Partner> partners = partnerJPARepository.findAll();
        return partners.stream()
                .map(Partner -> new PartnerSummaryDto(Partner.getId(),Partner.getName(),Partner.getCreatedAt(),Partner.getDescription()))
                .collect(Collectors.toList());

    }

    //파트너 상세 조회
    public Partner getPartnerById(Long id){
        Partner partner = partnerJPARepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Partner not found"));
        return partner;

    }
    //제휴업체 즐겨찾기
    @Transactional
    public void createPartnerBookmark(Long partnerId, UserDetailsImpl userDetails) {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Partner partner = partnerJPARepository.findById(partnerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PARTNER));

        Optional<PartnerBookmark> partnerBookmark = partnerBookmarkRepository.findByUserAndPartner(user, partner);

        if (partnerBookmark.isPresent()) {
            partnerBookmark.get().updateIsDeleted();
        } else {
            partnerBookmarkRepository.save(
                    PartnerBookmark.builder()
                            .user(user)
                            .partner(partner)
                            .isDeleted(false)
                            .build()
            );
        }
    }

    @Transactional
    public void deletePartnerBookmark(Long partnerId, UserDetailsImpl userDetails) {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Partner partner = partnerJPARepository.findById(partnerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PARTNER));
        PartnerBookmark partnerBookmark = partnerBookmarkRepository.findByUserAndPartner(user, partner)
                .orElseThrow( () -> new BusinessException(ErrorCode.NOT_FOUND_PARTNERBOOKMARK));
        partnerBookmarkRepository.delete(partnerBookmark);
    }



}
