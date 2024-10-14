package com.jnu.festival.domain.bookmark.service;

import com.jnu.festival.domain.bookmark.entity.ContentBookmark;
import com.jnu.festival.domain.bookmark.entity.PartnerBookmark;
import com.jnu.festival.domain.bookmark.repository.ContentBookmarkRepository;
import com.jnu.festival.domain.bookmark.repository.PartnerBookmarkRepository;
import com.jnu.festival.domain.content.entity.Content;
import com.jnu.festival.domain.content.repository.ContentRepository;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.partner.repository.PartnerRepository;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerBookmarkService {
    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;
    private final PartnerBookmarkRepository partnerBookmarkRepository;

    @Transactional
    public void createPartnerBookmark(Long partnerId, UserDetailsImpl userDetails) {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Partner partner = partnerRepository.findById(partnerId)
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
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PARTNER));
        PartnerBookmark partnerBookmark = partnerBookmarkRepository.findByUserAndPartner(user, partner)
                        .orElseThrow( () -> new BusinessException(ErrorCode.NOT_FOUND_PARTNERBOOKMARK));
        partnerBookmarkRepository.delete(partnerBookmark);
    }
}
