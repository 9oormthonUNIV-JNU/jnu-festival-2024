package com.jnu.festival.domain.timecapsule.service;

import com.jnu.festival.domain.timecapsule.dto.request.TimecapsuleRequestDto;
import com.jnu.festival.domain.timecapsule.dto.response.TimecapsuleDto;
import com.jnu.festival.domain.timecapsule.dto.response.TimecapsuleListDto;
import com.jnu.festival.domain.timecapsule.entity.Timecapsule;
import com.jnu.festival.domain.timecapsule.entity.TimecapsuleImage;
import com.jnu.festival.domain.timecapsule.repository.TimecapsuleImageRepository;
import com.jnu.festival.domain.timecapsule.repository.TimecapsuleRepository;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.config.S3Service;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.auth.UserDetailsImpl;
import com.jnu.festival.global.util.LocalDateTimeConvertUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimecapsuleService {
    private final UserRepository userRepository;
    private final TimecapsuleRepository timecapsuleRepository;
    private final S3Service s3Service;
    private final TimecapsuleImageRepository timecapsuleImageRepository;
    private final JavaMailSender javaMailSender;

    @Transactional
    public void createTimecapsule(TimecapsuleRequestDto request, List<MultipartFile> images, UserDetailsImpl userDetails) throws IOException {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        Timecapsule timecapsule = timecapsuleRepository.save(
                Timecapsule.builder()
                        .user(user)
                        .mailAddress(request.mailAddress())
                        .content(request.content())
                        .isPublic(request.isPublic())
                        .build()
        );

        if (images != null) {
            List<TimecapsuleImage> timecapsuleImages = new ArrayList<>();

            for (MultipartFile image : images) {
                String url = s3Service.upload(image, "timecapsule");
                TimecapsuleImage timecapsuleImage = TimecapsuleImage.builder()
                        .timecapsule(timecapsule)
                        .url(url)
                        .build();
                timecapsuleImages.add(timecapsuleImage);
            }

            timecapsuleImageRepository.saveAll(timecapsuleImages);
        }
    }

    public TimecapsuleListDto getTimecapsuleList(UserDetailsImpl userDetails) {
        List<TimecapsuleDto> myTimecapsuleDtos = new ArrayList<>();
        List<Timecapsule> timecapsules = timecapsuleRepository.findAllByIsPublicTrue();

        if (userDetails != null) {
            User user = userRepository.findByNickname(userDetails.getUsername())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
            List<Timecapsule> myTimecapsules = timecapsuleRepository.findAllByUser(user);

            for (Timecapsule myTimecapsule : myTimecapsules) {
                List<String> myTimecapsuleImages = timecapsuleImageRepository.findAllByTimecapsule(myTimecapsule).stream()
                        .map(TimecapsuleImage::getUrl)
                        .toList();

                TimecapsuleDto myTimecapsuleDto = TimecapsuleDto.builder()
                        .id(myTimecapsule.getId())
                        .nickname(user.getNickname())
                        .content(myTimecapsule.getContent())
                        .images(myTimecapsuleImages)
                        .createdAt(LocalDateTimeConvertUtil.convertUtcToLocalDateTIme(myTimecapsule.getCreatedAt()))
                        .build();

                myTimecapsuleDtos.add(myTimecapsuleDto);
            }

            timecapsules = timecapsules.stream()
                    .filter(timecapsule -> timecapsule.getUser() != user)
                    .toList();
        }

        List<TimecapsuleDto> timecapsuleDtos = new ArrayList<>();
        for (Timecapsule timecapsule : timecapsules) {
            List<String> timecapsuleImages = timecapsuleImageRepository.findAllByTimecapsule(timecapsule).stream()
                    .map(TimecapsuleImage::getUrl)
                    .toList();

            TimecapsuleDto timecapsuleDto = TimecapsuleDto.builder()
                    .id(timecapsule.getId())
                    .nickname(timecapsule.getUser().getNickname())
                    .content(timecapsule.getContent())
                    .images(timecapsuleImages)
                    .createdAt(LocalDateTimeConvertUtil.convertUtcToLocalDateTIme(timecapsule.getCreatedAt()))
                    .build();

            timecapsuleDtos.add(timecapsuleDto);
        }

        return TimecapsuleListDto.builder()
                .myTimecapsules(myTimecapsuleDtos)
                .timecapsules(timecapsuleDtos)
                .build();
    }


    @Transactional
    public void deleteTimecapsule(Long timecapsuleId, UserDetailsImpl userDetails) throws IOException {
        // 지우려는 사람의 정보를 가져옴.
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));


        // 관련된 사진을 S3에서 삭제
        // 1. 해당 타임캡슐을 DB에서 찾음.
        Timecapsule timecapsule = timecapsuleRepository.findById(timecapsuleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_TIMECAPSULE));

        // 타임캡슐 생성자와 현재 사용자가 동일한지 확인
        if (!timecapsule.getUser().equals(user)) {
            throw new BusinessException(ErrorCode.NOT_MATCH_USER);
        }

        // 2. 관련된 사진들을 타임캡슐로 찾는다.
        List<TimecapsuleImage> timecapsuleImages = timecapsuleImageRepository.findAllByTimecapsule(timecapsule);

        // 3. TimeCapsuleImage 엔티티들에서 URL 추출
//        List<String> urlList = timecapsuleImages.stream()
//                .map(TimecapsuleImage::getUrl)
//                .toList();
//
//        for (String url : urlList){
//            s3Service.delete(url);
//        }

        timecapsuleImageRepository.deleteAll(timecapsuleImages);
        timecapsuleRepository.delete(timecapsule);
    }

    @Scheduled(cron = "0 0 0 8 11 ?")
    public void sendMail() {
        List<Timecapsule> timecapsules = timecapsuleRepository.findAll();

        for (Timecapsule timecapsule: timecapsules) {
            List<String> images = timecapsuleImageRepository.findAllByTimecapsule(timecapsule).stream()
                    .map(TimecapsuleImage::getUrl).toList();

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            try {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
                mimeMessageHelper.setTo(timecapsule.getMailAddress());
                mimeMessageHelper.setSubject("[전대미문] 오늘을 기억하고, 추억을 선물하세요");
                StringBuilder text = new StringBuilder();
                text.append("<html><body><p>");
                text.append(timecapsule.getContent());
                text.append("</p>");
                for (String image: images) {
                    text.append("<img src='");
                    text.append(image);
                    text.append("' alt='image' />");
                }
                text.append("</body></html>");
                mimeMessageHelper.setText(text.toString(), true);
                javaMailSender.send(mimeMessage);

            } catch (MessagingException e) {
                throw new BusinessException(ErrorCode.SEND_MAIL_ERROR);
            }
        }
    }
}
