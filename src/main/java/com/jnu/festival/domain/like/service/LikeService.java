package com.jnu.festival.domain.like.service;

import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.like.repository.LikeJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class LikeService {

    private final LikeJPARepository likeJPARepository;


    //좋아요 등록
    /*public void createBoothLike(int boothId *//*CustomUserDetails userDetails*//*) throws Exception {
        User user = userJPARepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new UserNotExistException(ErrorCode.USER_NOT_EXIST));

        Product product = productJPARepository.findById(requestDTO.getProductId())
                .orElseThrow(() -> new ProductNotExistException(ErrorCode.PRODUCT_NOT_EXIST));

        Funding newFunding = Funding.builder()
                .user(user)
                .product(product)
                .event(requestDTO.getEvent())
                .startDate(requestDTO.getStartDate())
                .endDate(requestDTO.getEndDate())
                .state(State.Ongoing)
                .message(requestDTO.getMessage())
                .totalAmount(0)
                .build();

        fundingJPARepository.save(newFunding);
    }*/

    //좋아요 취소
}
