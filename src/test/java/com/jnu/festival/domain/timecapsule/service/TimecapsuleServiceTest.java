package com.jnu.festival.domain.timecapsule.service;

import com.jnu.festival.domain.timecapsule.dto.request.TimecapsuleRequestDto;
import com.jnu.festival.domain.timecapsule.entity.Timecapsule;
import com.jnu.festival.domain.timecapsule.repository.TimecapsuleImageRepository;
import com.jnu.festival.domain.timecapsule.repository.TimecapsuleRepository;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.config.S3Service;
import com.jnu.festival.global.security.UserDetailsImpl;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

public class TimecapsuleServiceTest {

    @InjectMocks
    private TimecapsuleService timecapsuleService;

    @Mock
    private S3Service s3Service;

    @Mock
    private User actualUser;

    @Mock
    private TimecapsuleRepository timecapsuleRepository;

    @Mock
    private TimecapsuleImageRepository timecapsuleImageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TimecapsuleRequestDto timecapsuleRequestDto;

    @Mock
    private Timecapsule timecapsule;

    @Mock
    private UserDetailsImpl principal;

    @BeforeEach
    void setUp() {
        principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        actualUser = principal.getUser();

        timecapsuleRequestDto = new TimecapsuleRequestDto("test", "test", true);
        timecapsule = Timecapsule.builder()
                .user(actualUser)
                .mailAddress(timecapsuleRequestDto.mailAddress())
                .content(timecapsuleRequestDto.content())
                .isPublic(timecapsuleRequestDto.isPublic())
                .build();
    }

    @Test
    @WithMockUser
    void 타임캡슐_생성_테스트() throws Exception {
        //given
        List<MultipartFile> images = List.of(
                new MockMultipartFile(
                        "images",
                        "image.jpg",
                        "image/jpeg",
                        "test-image".getBytes()
                ),
                new MockMultipartFile(
                        "images",
                        "image.jpg",
                        "image/jpeg",
                        "test-image".getBytes()
                )
        );

        //when
        when(userRepository.findByNickname(anyString())).thenReturn(Optional.of(actualUser));
        when(timecapsuleRepository.save(any(Timecapsule.class))).thenReturn(timecapsule);
        when(s3Service.upload(any(MultipartFile.class), anyString()))
                .thenReturn("https://s3.amazonaws.com/test-image-url");

        timecapsuleService.createTimecapsule(timecapsuleRequestDto, images, new UserDetailsImpl(actualUser));

        // then
        verify(timecapsuleRepository, times(1)).save(any(Timecapsule.class));
        verify(timecapsuleImageRepository, times(images.size())).saveAll(anyList());
        verify(s3Service, times(images.size())).upload(any(MultipartFile.class), eq("timecapsule"));
    }
}
