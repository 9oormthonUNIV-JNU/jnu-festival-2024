package com.jnu.festival.domain.like.service;

import com.jnu.festival.domain.bookmark.entity.BoothBookmark;
import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.repository.BoothRepository;
import com.jnu.festival.domain.like.dto.response.LikeDto;
import com.jnu.festival.domain.like.entity.Like;
import com.jnu.festival.domain.like.repository.LikeRepository;
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
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BoothRepository boothJPARepository;


    //좋아요 등록
    @Transactional
    public LikeDto createLike(Long boothId, UserDetailsImpl userDetails) throws Exception {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Booth booth = boothJPARepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));

        Optional<Like> like = likeRepository.findByUserAndBooth(user, booth);

        if (like.isPresent()) {
            like.get().updateIsDeleted();
        } else {
            likeRepository.save(
                    Like.builder()
                            .user(user)
                            .booth(booth)
                            .isDeleted(false)
                            .build()
            );
        }

        return LikeDto.builder()
                .likeCount(likeRepository.countAllByBoothAndIsDeletedFalse(booth))
                .build();
    }

    //좋아요 취소
    public LikeDto deleteLike(Long boothId, UserDetailsImpl userDetails) throws Exception {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Booth booth = boothJPARepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));
        Like like = likeRepository.findByUserAndBooth(user, booth)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_LIKE));
        likeRepository.delete(like);

        return LikeDto.builder()
                .likeCount(likeRepository.countAllByBoothAndIsDeletedFalse(booth))
                .build();
    }
}
