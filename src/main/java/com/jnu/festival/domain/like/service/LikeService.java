package com.jnu.festival.domain.like.service;

import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.repository.BoothJPARepository;
import com.jnu.festival.domain.like.dto.LikeResponseDTO;
import com.jnu.festival.domain.like.entity.Like;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.like.repository.LikeJPARepository;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class LikeService {

    private final LikeJPARepository likeJPARepository;
    private final UserRepository userRepository;
    private final BoothJPARepository boothJPARepository;


    //좋아요 등록
    public void createBoothLike(UserDetailsImpl userDetails, Long boothId) throws Exception {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        Booth booth = boothJPARepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));

        Like newLike = Like.builder()
                .user(user)
                .booth(booth).build();

        likeJPARepository.save(newLike);
    }

    //좋아요 취소
    public LikeResponseDTO updateboothlike(UserDetailsImpl userDetails, Long boothId, Long likeId) throws Exception {

        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        Booth booth = boothJPARepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));

        Like like = likeJPARepository.findById(likeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_LIKE));

        // 좋아요가 현재 로그인한 사용자와 해당 부스에 대한 것인지 확인
        if (!like.getUser().getId().equals(user.getId()) || !like.getBooth().getId().equals(booth.getId())) {
            throw new Exception("Unauthorized action");
        }

        // 좋아요 취소: is_deleted 필드를 true로 설정
        like.setIsDeleted(true);
        likeJPARepository.save(like);


        return new LikeResponseDTO(like);
    }
}
