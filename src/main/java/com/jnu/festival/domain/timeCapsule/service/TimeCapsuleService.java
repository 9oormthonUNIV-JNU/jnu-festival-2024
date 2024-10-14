package com.jnu.festival.domain.timeCapsule.service;

import com.jnu.festival.domain.timeCapsule.dto.TimeCapsuleRequestDto;
import com.jnu.festival.domain.timeCapsule.dto.TimecapsuleListResponseDto;
import com.jnu.festival.domain.timeCapsule.dto.TimecapsuleResponseDto;
import com.jnu.festival.domain.timeCapsule.entity.TimeCapsule;
import com.jnu.festival.domain.timeCapsule.entity.TimeCapsuleImage;
import com.jnu.festival.domain.timeCapsule.repository.TimeCapsuleImageRepository;
import com.jnu.festival.domain.timeCapsule.repository.TimeCapsuleRepository;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.config.S3Service;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeCapsuleService {
    private final UserRepository userRepository;
    private final TimeCapsuleRepository timeCapsuleRepository;
    private final S3Service s3Service;
    private final TimeCapsuleImageRepository timeCapsuleImageRepository;

    @Transactional
    public void createTimeCapsule(TimeCapsuleRequestDto request, List<MultipartFile> images, UserDetailsImpl userDetails) throws IOException {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        TimeCapsule timeCapsule = timeCapsuleRepository.save(
                TimeCapsule.builder()
                        .user(user)
                        .mailAddress(request.mail_address())
                        .content(request.content())
                        .isPublic(request.is_public())
                        .build()
        );

        List<String> urlList = s3Service.uploadImages(images, "timeCapsule");

        // 각 URL을 사용하여 TimeCapsuleImage 생성 및 저장
        for (String url : urlList) {
            TimeCapsuleImage timeCapsuleImage = TimeCapsuleImage.builder()
                    .timeCapsule(timeCapsule)
                    .url(url)
                    .build();

            // TimeCapsuleImage를 저장
            timeCapsuleImageRepository.save(timeCapsuleImage);
        }
    }

    @Transactional
    public void deleteTimeCapsule(Long timecapsuleId, UserDetailsImpl userDetails) throws IOException{
        // 지우려는 사람의 정보를 가져옴.
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));


        // 관련된 사진을 S3에서 삭제
        // 1. 해당 타임캡슐을 DB에서 찾음.
        TimeCapsule timecapsule = timeCapsuleRepository.findById(timecapsuleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_TIMECAPSULE));

        // 타임캡슐 생성자와 현재 사용자가 동일한지 확인
        if (!timecapsule.getUser().equals(user)) {
            throw new BusinessException(ErrorCode.NOT_MATCH_USER);
        }

        // 2. 관련된 사진들을 타임캡슐로 찾는다.
        List<TimeCapsuleImage> timecapsuleImages = timeCapsuleImageRepository.findTimeCapsuleImagesByTimeCapsule(timecapsule);

        // 3. TimeCapsuleImage 엔티티들에서 URL 추출
        List<String> urlList = timecapsuleImages.stream()
                .map(TimeCapsuleImage::getUrl)
                .collect(Collectors.toList());

        for (String url : urlList){
            s3Service.delete(url);
        }
        timeCapsuleImageRepository.deleteAll(timecapsuleImages);
        timeCapsuleRepository.delete(timecapsule);
    }

    public List<TimecapsuleResponseDto> getMytimecapsules(UserDetailsImpl userDetails){
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        List<TimeCapsule> myTimecapsules = timeCapsuleRepository.findByUser(user);

        return myTimecapsules.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TimecapsuleResponseDto> getTimecapsules(UserDetailsImpl userDetails){
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        List<TimeCapsule> timecapsules = timeCapsuleRepository.findByIsPublicTrueAndIdIsNot(user);

        return timecapsules.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TimecapsuleResponseDto convertToDto(TimeCapsule timecapsule){
        List<TimeCapsuleImage> timeCapsuleImages = timeCapsuleImageRepository.findTimeCapsuleImagesByTimeCapsule(timecapsule);
        List<String> imageUrls = timeCapsuleImages.stream()
                .map(TimeCapsuleImage::getUrl)
                .collect(Collectors.toList());

        return TimecapsuleResponseDto.builder()
                .id(timecapsule.getId())
                .nickname(timecapsule.getUser().getNickname())
                .content(timecapsule.getContent())
                .imageUrls(imageUrls)
                .created_at(timecapsule.getCreatedAt().toLocalDate())
                .build();
    }

    public TimecapsuleListResponseDto mapResponses(List<TimecapsuleResponseDto> my_timecapsules,List<TimecapsuleResponseDto> timecapsules){
        TimecapsuleListResponseDto response = TimecapsuleListResponseDto.builder()
                .my_timecapsules(my_timecapsules)
                .timecapsules(timecapsules)
                .build();
        return response;
    }

}
