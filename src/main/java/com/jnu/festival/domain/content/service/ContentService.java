package com.jnu.festival.domain.content.service;

import com.jnu.festival.domain.bookmark.repository.ContentBookmarkRepository;
import com.jnu.festival.domain.content.dto.ContentDto;
import com.jnu.festival.domain.content.dto.ContentListDto;
import com.jnu.festival.domain.content.entity.Content;
import com.jnu.festival.domain.content.entity.ContentImage;
import com.jnu.festival.domain.content.repository.ContentImageRepository;
import com.jnu.festival.domain.content.repository.ContentRepository;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final ContentBookmarkRepository contentBookmarkRepository;
    private final ContentImageRepository contentImageRepository;

    public List<ContentListDto> readContentList(UserDetailsImpl userDetails) {
        List<Content> contents = contentRepository.findAll();
        if (userDetails != null) {
            User user = userRepository.findByNickname(userDetails.getUsername())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
            List<Long> contentBookmarkIds = contentBookmarkRepository.findAllByUserAndIsDeleted(user).stream()
                    .map(Content::getId)
                    .toList();

            return contents.stream()
                    .map(content -> ContentListDto.builder()
                            .id(content.getId())
                            .title(content.getTitle())
                            .description(content.getDescription())
                            .bookmark(contentBookmarkIds.contains(content.getId()))
                            .createdAt(content.getCreatedAt())
                            .build())
                    .toList();
        }
        return contents.stream()
                .map(content -> ContentListDto.builder()
                        .id(content.getId())
                        .title(content.getTitle())
                        .description(content.getDescription())
                        .bookmark(false)
                        .createdAt(content.getCreatedAt())
                        .build())
                .toList();
    }

    public ContentDto readContent(Long contentId, UserDetailsImpl userDetails) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CONTENT));
        List<String> contentImages = contentImageRepository.findAllByContent(content).stream()
                .map(ContentImage::getUrl)
                .toList();

        if (userDetails != null) {
            User user = userRepository.findByNickname(userDetails.getUsername())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
            Long contentBookmarkId = contentBookmarkRepository.findByUserAndIsDeleted(user)
                    .map(Content::getId).orElse(null);
            return ContentDto.builder()
                    .id(content.getId())
                    .title(content.getTitle())
                    .description(content.getDescription())
                    .images(contentImages)
                    .bookmark(content.getId().equals(contentBookmarkId))
                    .createdAt(content.getCreatedAt())
                    .build();
        }
        return ContentDto.builder()
                .id(content.getId())
                .title(content.getTitle())
                .description(content.getDescription())
                .images(contentImages)
                .bookmark(false)
                .createdAt(content.getCreatedAt())
                .build();
    }
}
