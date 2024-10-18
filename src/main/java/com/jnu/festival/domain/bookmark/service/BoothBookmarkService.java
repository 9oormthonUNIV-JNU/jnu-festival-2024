package com.jnu.festival.domain.bookmark.service;

import com.jnu.festival.domain.bookmark.entity.BoothBookmark;
import com.jnu.festival.domain.bookmark.entity.ContentBookmark;
import com.jnu.festival.domain.bookmark.repository.BoothBookmarkRepository;
import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.repository.BoothRepository;
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
public class BoothBookmarkService {
    private final BoothBookmarkRepository boothBookmarkRepository;
    private final UserRepository userRepository;
    private final BoothRepository boothRepository;

    //부스 즐겨찾기
    @Transactional
    public void createBoothBookmark(Long boothId, UserDetailsImpl userDetails) throws Exception {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));

        Optional<BoothBookmark> boothBookmark = boothBookmarkRepository.findByUserAndBooth(user, booth);

        if (boothBookmark.isPresent()) {
            boothBookmark.get().updateIsDeleted();
        } else {
            boothBookmarkRepository.save(
                    BoothBookmark.builder()
                            .user(user)
                            .booth(booth)
                            .isDeleted(false)
                            .build()
            );
        }
    }

    //부스 즐겨찾기 취소
    @Transactional
    public void deleteBoothBookmark(Long boothId, UserDetailsImpl userDetails) throws Exception {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));
        BoothBookmark boothBookmark = boothBookmarkRepository.findByUserAndBooth(user, booth)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTHBOOKMARK));
        boothBookmarkRepository.delete(boothBookmark);
    }
}
