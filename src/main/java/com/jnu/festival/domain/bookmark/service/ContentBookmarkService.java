package com.jnu.festival.domain.bookmark.service;

import com.jnu.festival.domain.bookmark.entity.ContentBookmark;
import com.jnu.festival.domain.bookmark.entity.PartnerBookmark;
import com.jnu.festival.domain.bookmark.repository.ContentBookmarkRepository;
import com.jnu.festival.domain.content.entity.Content;
import com.jnu.festival.domain.content.repository.ContentRepository;
import com.jnu.festival.domain.partner.entity.Partner;
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
public class ContentBookmarkService {
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;
    private final ContentBookmarkRepository contentBookmarkRepository;

    @Transactional
    public void createContentBookmark(Long contentId, UserDetailsImpl userDetails) {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CONTENT));

        Optional<ContentBookmark> contentBookmark = contentBookmarkRepository.findByUserAndContent(user, content);

        if (contentBookmark.isPresent()) {
            contentBookmark.get().updateIsDeleted();
        } else {
            contentBookmarkRepository.save(
                    ContentBookmark.builder()
                            .user(user)
                            .content(content)
                            .isDeleted(false)
                            .build()
            );
        }
    }

    @Transactional
    public void deleteContentBookmark(Long contentId, UserDetailsImpl userDetails) {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CONTENT));
        ContentBookmark contentBookmark = contentBookmarkRepository.findByUserAndContent(user, content)
                        .orElseThrow( () -> new BusinessException(ErrorCode.NOT_FOUND_CONTENTBOOKMARK));
        contentBookmarkRepository.delete(contentBookmark);
    }
}
