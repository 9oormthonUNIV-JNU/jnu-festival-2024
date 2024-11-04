package com.jnu.festival.domain.admin.service;

import com.jnu.festival.domain.admin.dto.request.BoothRequestDto;
import com.jnu.festival.domain.admin.dto.request.ContentRequestDto;
import com.jnu.festival.domain.admin.dto.request.PartnerRequestDto;
import com.jnu.festival.domain.admin.dto.request.ZoneRequestDto;
import com.jnu.festival.domain.admin.dto.response.FeedbackDto;
import com.jnu.festival.domain.admin.dto.response.FeedbackListDto;
import com.jnu.festival.domain.bookmark.repository.BoothBookmarkRepository;
import com.jnu.festival.domain.bookmark.repository.ContentBookmarkRepository;
import com.jnu.festival.domain.bookmark.repository.PartnerBookmarkRepository;
import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.entity.BoothCategory;
import com.jnu.festival.domain.booth.entity.BoothImage;
import com.jnu.festival.domain.booth.entity.Period;
import com.jnu.festival.domain.booth.repository.BoothImageRepository;
import com.jnu.festival.domain.booth.repository.BoothRepository;
import com.jnu.festival.domain.comment.entity.Comment;
import com.jnu.festival.domain.comment.repository.CommentRepository;
import com.jnu.festival.global.common.Location;
import com.jnu.festival.domain.content.entity.Content;
import com.jnu.festival.domain.content.entity.ContentImage;
import com.jnu.festival.domain.content.repository.ContentImageRepository;
import com.jnu.festival.domain.content.repository.ContentRepository;
import com.jnu.festival.domain.feedback.entity.Feedback;
import com.jnu.festival.domain.feedback.entity.FeedbackCategory;
import com.jnu.festival.domain.feedback.entity.FeedbackImage;
import com.jnu.festival.domain.feedback.repository.FeedbackImageRepository;
import com.jnu.festival.domain.feedback.repository.FeedbackRepository;
import com.jnu.festival.domain.like.repository.LikeRepository;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.partner.entity.PartnerImage;
import com.jnu.festival.domain.partner.repository.PartnerImageRepository;
import com.jnu.festival.domain.partner.repository.PartnerRepository;
import com.jnu.festival.domain.timecapsule.entity.Timecapsule;
import com.jnu.festival.domain.timecapsule.repository.TimecapsuleImageRepository;
import com.jnu.festival.domain.timecapsule.repository.TimecapsuleRepository;
import com.jnu.festival.domain.zone.entity.Zone;
import com.jnu.festival.domain.zone.repository.ZoneRepository;
import com.jnu.festival.global.config.S3Service;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.util.LocalDateTimeConvertUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ZoneRepository zoneRepository;
    private final PartnerRepository partnerRepository;
    private final PartnerImageRepository partnerImageRepository;
    private final PartnerBookmarkRepository partnerBookmarkRepository;
    private final ContentRepository contentRepository;
    private final ContentImageRepository contentImageRepository;
    private final ContentBookmarkRepository contentBookmarkRepository;
    private final BoothRepository boothRepository;
    private final BoothImageRepository boothImageRepository;
    private final BoothBookmarkRepository boothBookmarkRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final TimecapsuleRepository timecapsuleRepository;
    private final TimecapsuleImageRepository timecapsuleImageRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackImageRepository feedbackImageRepository;
    private final S3Service s3Service;

    @Transactional
    public void createZone(ZoneRequestDto request) {

//        if (Location.from(request.location()) == null) {
//            throw new BusinessException(ErrorCode.INVALID_LOCATION);
//        }

        zoneRepository.save(
                Zone.builder()
                        .name(request.name())
                        .location(Location.from(request.location()))
                        .description(request.description())
                        .build()
        );
    }

    @Transactional
    public void deleteZone(Long zoneId) {
        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ZONE));
        zoneRepository.delete(zone);
    }

    @Transactional
    public void createPartner(PartnerRequestDto request, List<MultipartFile> images) throws IOException {
        Partner partner = partnerRepository.save(
                Partner.builder()
                        .name(request.name())
                        .location(request.location())
                        .startDate(request.startDate())
                        .endDate(request.endDate())
                        .description(request.description())
                        .build()
        );
        System.out.println(2);

        if (images != null) {
            System.out.println(3);
            List<PartnerImage> partnerImages = new ArrayList<>();

            for (MultipartFile image : images) {
                String url = s3Service.upload(image, "partner");
                System.out.println(4);
                PartnerImage partnerImage = PartnerImage.builder()
                        .partner(partner)
                        .url(url)
                        .build();
                partnerImages.add(partnerImage);
            }

            partnerImageRepository.saveAll(partnerImages);
        }
    }

    @Transactional
    public void deletePartner(Long partnerId) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PARTNER));

        partnerBookmarkRepository.deleteAllByPartner(partner);
        partnerImageRepository.deleteAllByPartner(partner);
        partnerRepository.delete(partner);
    }

    @Transactional
    public void createContent(ContentRequestDto request, List<MultipartFile> images) throws IOException {
        Content content = contentRepository.save(
                Content.builder()
                        .title(request.title())
                        .description(request.description())
                        .build()
        );

        if (images != null) {
            List<ContentImage> contentImages = new ArrayList<>();

            for (MultipartFile image : images) {
                String url = s3Service.upload(image, "content");
                ContentImage contentImage = ContentImage.builder()
                        .content(content)
                        .url(url)
                        .build();
                contentImages.add(contentImage);
            }

            contentImageRepository.saveAll(contentImages);
        }
    }

    @Transactional
    public void deleteContent(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CONTENT));

        contentBookmarkRepository.deleteAllByContent(content);
        contentImageRepository.deleteAllByContent(content);
        contentRepository.delete(content);
    }

    @Transactional
    public void createBooth(BoothRequestDto request, List<MultipartFile> images) throws IOException {
        if (Location.from(request.location()) == null) {
            throw new BusinessException(ErrorCode.INVALID_LOCATION);
        }

        if (BoothCategory.from(request.category()) == null) {
            throw new BusinessException(ErrorCode.INVALID_LOCATION);
        }

        Booth booth = boothRepository.save(
                Booth.builder()
                        .name(request.name())
                        .location(Location.from(request.location()))
                        .index(request.index())
                        .startDate(request.startDate())
                        .endDate(request.endDate())
                        .startTime(request.startTime())
                        .endTime(request.endTime())
                        .description(request.description())
                        .category(BoothCategory.from(request.category()))
                        .period(Period.from(request.period()))
                        .build()
        );

        if (images != null) {
            List<BoothImage> boothImages = new ArrayList<>();

            for (MultipartFile image : images) {
                String url = s3Service.upload(image, "booth");
                BoothImage boothImage = BoothImage.builder()
                        .booth(booth)
                        .url(url)
                        .build();
                boothImages.add(boothImage);
            }

            boothImageRepository.saveAll(boothImages);
        }
    }

    @Transactional
    public void deleteBooth(Long boothId) {
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));

        boothBookmarkRepository.deleteAllByBooth(booth);
        likeRepository.deleteAllByBooth(booth);
        commentRepository.deleteAllByBooth(booth);
        boothImageRepository.deleteAllByBooth(booth);
        boothRepository.delete(booth);
    }

    @Transactional
    public void deleteComment(Long boothId, Long commentId) {
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_BOOTH));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));

        if (!comment.getBooth().equals(booth)) {
            throw new BusinessException(ErrorCode.NOT_MATCH_BOOTH);
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public void deleteTimecapsule(Long timecapsuleId) {
        Timecapsule timecapsule = timecapsuleRepository.findById(timecapsuleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_TIMECAPSULE));

        timecapsuleImageRepository.deleteAllByTimecapsule(timecapsule);
        timecapsuleRepository.delete(timecapsule);
    }

    public List<FeedbackListDto> readFeedbackList(String category) {
        if (!category.isEmpty() && FeedbackCategory.from(category) == null) {
            throw new BusinessException(ErrorCode.INVALID_CATEGORY);
        }

        List<Feedback> feedbacks = feedbackRepository.findAllByCategory(FeedbackCategory.from(category));
        return feedbacks.stream()
                .map(feedback -> FeedbackListDto.builder()
                        .id(feedback.getId())
                        .nickname(feedback.getUser().getNickname())
                        .title(feedback.getTitle())
                        .createdAt(LocalDateTimeConvertUtil.convertUtcToLocalDateTIme(feedback.getCreatedAt()))
                        .build())
                .toList();
    }

    public FeedbackDto readFeedback(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_FEEDBACK));
        List<String> feedbackImages = feedbackImageRepository.findAllByFeedback(feedback).stream()
                .map(FeedbackImage::getUrl).toList();
        return FeedbackDto.builder()
                .id(feedback.getId())
                .nickname(feedback.getUser().getNickname())
                .title(feedback.getTitle())
                .content(feedback.getContent())
                .images(feedbackImages)
                .createdAt(LocalDateTimeConvertUtil.convertUtcToLocalDateTIme(feedback.getCreatedAt()))
                .build();
    }
}
