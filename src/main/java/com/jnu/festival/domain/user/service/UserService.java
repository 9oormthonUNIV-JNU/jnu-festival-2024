package com.jnu.festival.domain.user.service;

import com.jnu.festival.domain.bookmark.dto.response.BoothBookmarkListDto;
import com.jnu.festival.domain.bookmark.dto.response.ContentBookmarkListDto;
import com.jnu.festival.domain.bookmark.dto.response.PartnerBookmarkListDto;
import com.jnu.festival.domain.bookmark.repository.BoothBookmarkRepository;
import com.jnu.festival.domain.bookmark.repository.ContentBookmarkRepository;
import com.jnu.festival.domain.bookmark.repository.PartnerBookmarkRepository;
import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.content.entity.Content;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.user.dto.request.UserRegisterDto;
import com.jnu.festival.domain.user.dto.response.UserDto;
import com.jnu.festival.domain.user.entity.Role;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.auth.UserDetailsImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PartnerBookmarkRepository partnerBookmarkRepository;
    private final ContentBookmarkRepository contentBookmarkRepository;
    private final BoothBookmarkRepository boothBookmarkRepository;

    public void signup(UserRegisterDto request) {
        Optional<User> registeredUser = userRepository.findByNickname(request.nickname());

        if (registeredUser.isEmpty()) {
            User user = User.builder()
                    .nickname(request.nickname())
                    .password(passwordEncoder.encode(request.password()))
                    .role(Role.ROLE_USER)
                    .build();
            userRepository.save(user);
        }
    }

    @Transactional
    public void updateAccessToken(UserDetailsImpl userDetails, String accessToken) {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        user.updateAccessToken(accessToken);
    }

    public void checkAdmin(HttpServletRequest request) {
        Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(c-> c.getName().equals("Authorization"))
                .findFirst();

        if (cookie.isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_HEADER_ERROR);
        }

        String accessToken = cookie.get().getValue();
        String role = userRepository.findRoleByAccessToken(accessToken)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        if (role.equals("ROLE_USER")) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }
    }

    public UserDto readUser(UserDetailsImpl userDetails) {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        return UserDto.builder()
                .nickname(user.getNickname())
                .build();
    }

    public List<PartnerBookmarkListDto> readPartnerBookmarkList(UserDetailsImpl userDetails) {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        List<Partner> partners = partnerBookmarkRepository.findAllByUserAndIsDeleted(user);
        return partners.stream()
                .map(partner -> PartnerBookmarkListDto.builder()
                        .id(partner.getId())
                        .name(partner.getName())
                        .description(partner.getDescription())
                        .bookmark(true)
                        .createdAt(partner.getCreatedAt())
                        .build()).toList();
    }

    public List<ContentBookmarkListDto> readContentBookmarkList(UserDetailsImpl userDetails) {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        List<Content> contents = contentBookmarkRepository.findAllByUserAndIsDeleted(user);
        return contents.stream()
                .map(content -> ContentBookmarkListDto.builder()
                        .id(content.getId())
                        .title(content.getTitle())
                        .description(content.getDescription())
                        .bookmark(true)
                        .createdAt(content.getCreatedAt())
                        .build()).toList();
    }

    public List<BoothBookmarkListDto> readBoothBookmark(UserDetailsImpl userDetails) {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        List<Booth> booths = boothBookmarkRepository.findAllByUserAndIsDeleted(user);
        return booths.stream()
                .map(booth -> BoothBookmarkListDto.builder()
                        .id(booth.getId())
                        .name(booth.getName())
                        .location(booth.getLocation())
                        .index(booth.getIndex())
                        .startDate(booth.getStartDate())
                        .endDate(booth.getEndDate())
                        .startTime(booth.getStartTime())
                        .endTime(booth.getEndTime())
                        .bookmark(true)
                        .build()).toList();
    }
}
