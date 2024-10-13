package com.jnu.festival.domain.timeCapsule.service;

import com.jnu.festival.domain.timeCapsule.dto.TimeCapsuleRequestDto;
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
import java.util.List;

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

}
