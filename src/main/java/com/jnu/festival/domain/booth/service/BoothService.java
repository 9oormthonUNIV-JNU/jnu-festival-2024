package com.jnu.festival.domain.booth.service;


import com.jnu.festival.domain.bookmark.repository.BoothBookmarkRepository;
import com.jnu.festival.domain.booth.dto.BoothDto;
import com.jnu.festival.domain.booth.dto.BoothListDto;
import com.jnu.festival.domain.booth.dto.BoothRankListDto;
import com.jnu.festival.domain.booth.dto.BoothSearchListDto;
import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.entity.BoothCategory;
import com.jnu.festival.domain.booth.entity.BoothImage;
import com.jnu.festival.domain.booth.entity.Period;
import com.jnu.festival.domain.booth.repository.BoothImageRepository;
import com.jnu.festival.domain.booth.repository.BoothRepository;
import com.jnu.festival.domain.common.Location;
import com.jnu.festival.domain.like.repository.LikeRepository;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoothService {
    private final BoothRepository boothRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final BoothBookmarkRepository boothBookmarkRepository;
    private final BoothImageRepository boothImageRepository;

    //카테고리별 부스 목록 조회
    public List<BoothListDto> readBoothList(String location, String period, String category, UserDetailsImpl userDetails) {
        List<Booth> booths = boothRepository.findAllByLocationAndAndCategoryAndPeriod(Location.from(location), BoothCategory.from(category), Period.from(period));

        if (userDetails != null) {
            User user = userRepository.findByNickname(userDetails.getUsername())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
//            List<Long> boothBookmarkIds = boothBookmarkRepository.findAllByUserAndIsDeleted(user).stream()
//                    .map(Booth::getId)
//                    .toList();
            List<Long> likeIds = likeRepository.findAllByUserAndIsDeleted(user).stream()
                    .map(Booth::getId)
                    .toList();

            return booths.stream()
                    .map(booth -> BoothListDto.builder()
                            .id(booth.getId())
                            .name(booth.getName())
                            .location(booth.getLocation().getValue())
                            .index(booth.getIndex())
                            .startDate(booth.getStartDate())
                            .endDate(booth.getEndDate())
                            .startTime(booth.getStartTime())
                            .endTime(booth.getEndTime())
                            .like(likeIds.contains(booth.getId()))
                            .likeCount(likeRepository.countAllByBoothAndIsDeletedFalse(booth))
                            .build())
                    .toList();
        }
        return booths.stream()
                .map(booth -> BoothListDto.builder()
                        .id(booth.getId())
                        .name(booth.getName())
                        .location(booth.getLocation().getValue())
                        .index(booth.getIndex())
                        .startDate(booth.getStartDate())
                        .endDate(booth.getEndDate())
                        .startTime(booth.getStartTime())
                        .endTime(booth.getEndTime())
                        .like(false)
                        .likeCount(likeRepository.countAllByBoothAndIsDeletedFalse(booth))
                        .build())
                .toList();
    }

    // 부스 상세 조회
    public BoothDto readBooth(Long boothId, UserDetailsImpl userDetails) {
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));
        List<String> boothImages = boothImageRepository.findAllByBooth(booth).stream()
                .map(BoothImage::getUrl)
                .toList();

        if (userDetails != null) {
            User user = userRepository.findByNickname(userDetails.getUsername())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
            Long likeId = likeRepository.findByUserAndBoothAndIsDeleted(user, booth)
                    .map(Booth::getId).orElse(null);
            Long boothBookmarkId = boothBookmarkRepository.findByUserAndBoothAndIsDeleted(user, booth)
                    .map(Booth::getId).orElse(null);
            return BoothDto.builder()
                    .id(booth.getId())
                    .name(booth.getName())
                    .location(booth.getLocation().getValue())
                    .index(booth.getIndex())
                    .startDate(booth.getStartDate())
                    .endDate(booth.getEndDate())
                    .startTime(booth.getStartTime())
                    .endTime(booth.getEndTime())
                    .like(booth.getId().equals(likeId))
                    .likeCount(likeRepository.countAllByBoothAndIsDeletedFalse(booth))
                    .bookmark(booth.getId().equals(boothBookmarkId))
                    .description(booth.getDescription())
                    .images(boothImages)
                    .build();
        }
        return BoothDto.builder()
                .id(booth.getId())
                .name(booth.getName())
                .location(booth.getLocation().getValue())
                .index(booth.getIndex())
                .startDate(booth.getStartDate())
                .endDate(booth.getEndDate())
                .startTime(booth.getStartTime())
                .endTime(booth.getEndTime())
                .like(false)
                .likeCount(likeRepository.countAllByBoothAndIsDeletedFalse(booth))
                .bookmark(false)
                .description(booth.getDescription())
                .images(boothImages)
                .build();
    }

    public List<BoothSearchListDto> readBoothSearchList(String keyword) {
        return boothRepository.findAllByNameContains(keyword).stream()
                .map(booth -> BoothSearchListDto.builder()
                        .id(booth.getId())
                        .name(booth.getName())
                        .build())
                .toList();
    }

    public List<BoothRankListDto> readBoothRankList() {
        return likeRepository.findTop5ByLike(PageRequest.of(0, 5)).stream()
                .map(booth -> BoothRankListDto.builder()
                        .id(booth.getId())
                        .name(booth.getName())
                        .likeCount(likeRepository.countAllByBoothAndIsDeletedFalse(booth))
                        .build())
                .toList();
    }
}
