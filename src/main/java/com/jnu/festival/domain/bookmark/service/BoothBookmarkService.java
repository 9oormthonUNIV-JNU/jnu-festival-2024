package com.jnu.festival.domain.bookmark.service;


import com.jnu.festival.domain.bookmark.dto.response.BoothBookmarkResponseDTO;
import com.jnu.festival.domain.bookmark.entity.BoothBookmark;
import com.jnu.festival.domain.bookmark.repository.BoothBookmarkRepository;
import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.repository.BoothJPARepository;
import com.jnu.festival.domain.like.dto.LikeResponseDTO;
import com.jnu.festival.domain.like.entity.Like;
import com.jnu.festival.domain.user.entity.User;
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
public class BoothBookmarkService {

    private final BoothBookmarkRepository boothBookmarkRepository;
    private final UserRepository userRepository;
    private final BoothJPARepository boothJPARepository;

    //부스 즐겨찾기
    public void createBoothBookmark(UserDetailsImpl userDetails, Long boothId) throws Exception {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        Booth booth = boothJPARepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));

        BoothBookmark newBoothBookmark = BoothBookmark.builder()
                .user(user)
                .booth(booth).build();

        boothBookmarkRepository.save(newBoothBookmark);
    }


    //부스 즐겨찾기 취소
    public BoothBookmarkResponseDTO updateIsDeleted(UserDetailsImpl userDetails, Long boothId) throws Exception {

        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        Booth booth = boothJPARepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));

        BoothBookmark boothBookmark = boothBookmarkRepository.findByUserAndBooth(userDetails.getUser().getId(), boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_LIKE));

        // 좋아요가 현재 로그인한 사용자와 해당 부스에 대한 것인지 확인
        if (!boothBookmark.getUser().getId().equals(user.getId()) || !boothBookmark.getBooth().getId().equals(booth.getId())) {
            throw new Exception("Unauthorized action");
        }

        // 좋아요 취소: is_deleted 필드를 true로 설정
        boothBookmark.setIsDeleted(true);
        boothBookmarkRepository.save(boothBookmark);


        return new BoothBookmarkResponseDTO(boothBookmark);
    }


}
