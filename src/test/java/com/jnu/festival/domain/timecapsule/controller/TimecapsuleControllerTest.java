package com.jnu.festival.domain.timecapsule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.festival.domain.timecapsule.MockCustomUser;
import com.jnu.festival.domain.timecapsule.dto.request.TimecapsuleRequestDto;
import com.jnu.festival.domain.timecapsule.service.TimecapsuleService;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;

@WebMvcTest(controllers = TimecapsuleController.class)
@ExtendWith(MockitoExtension.class)
public class TimecapsuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimecapsuleService timecapsuleService;

    @DisplayName("타임캡슐을 등록한다.")
    @Test
    @MockCustomUser
    void createTimecapsule() throws Exception {
        // given
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        TimecapsuleRequestDto dto = new TimecapsuleRequestDto("test", "test", true);
        MockMultipartFile json = new MockMultipartFile(
                "request",
                "",
                "application/json",
                new ObjectMapper().writeValueAsString(dto).getBytes()
        );

        MockMultipartFile images = new MockMultipartFile(
                "images",
                "image.jpg",
                "image/jpeg",
                "test-image".getBytes()
        );

        // when, then
        mockMvc.perform(multipart("/api/v1/timecapsules")
                        .file(json)
                        .file(images)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(csrf())
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                principal, "test", principal.getAuthorities())))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        // 서비스 메서드 호출 확인
        verify(timecapsuleService).createTimecapsule(any(TimecapsuleRequestDto.class), anyList(),
                any(UserDetailsImpl.class));
    }

    @DisplayName("타임캡슐을 등록할 때 이미지 없이 요청을 보낸다.")
    @Test
    @MockCustomUser
    void 이미지_제외_타임캡슐_저장() throws Exception {
        // given
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        TimecapsuleRequestDto dto = new TimecapsuleRequestDto("test", "test", true);
        MockMultipartFile json = new MockMultipartFile(
                "request",
                "",
                "application/json",
                new ObjectMapper().writeValueAsString(dto).getBytes()
        );

        MockMultipartFile emptyImage = new MockMultipartFile(
                "images", // 필드 이름
                "", // 파일 이름
                "image/jpeg", // 콘텐츠 타입
                new byte[0] // 빈 바이트 배열
        );

        // when, then
        mockMvc.perform(multipart("/api/v1/timecapsules")
                        .file(json)
                        .file(emptyImage)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(csrf())
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                principal, "test", principal.getAuthorities()))))
                .andExpect(MockMvcResultMatchers.status().isOk()); // 200 상태 코드 확인

        // 서비스 메서드 호출 확인
        verify(timecapsuleService).createTimecapsule(any(TimecapsuleRequestDto.class), anyList(),
                any(UserDetailsImpl.class)); // images가 null인 경우 확인
    }

    @DisplayName("메일 주소가 null일 경우 서버 내부 에러를 반환한다.")
    @Test
    @MockCustomUser
    void 메일_주소_Null_예외_테스트() throws Exception {
        // given
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        TimecapsuleRequestDto dto = new TimecapsuleRequestDto(null, "test content", true); // mailAddress를 null로 설정
        MockMultipartFile json = new MockMultipartFile(
                "request",
                "",
                "application/json",
                new ObjectMapper().writeValueAsString(dto).getBytes()
        );

        MockMultipartFile images = new MockMultipartFile(
                "images",
                "image.jpg",
                "image/jpeg",
                "test-image".getBytes()
        );

        // when
        doThrow(new BusinessException(ErrorCode.INVALID_ARGUMENT))
                .when(timecapsuleService).createTimecapsule(argThat(argument ->
                        argument.mailAddress() == null), anyList(), any());

        // then
        mockMvc.perform(multipart("/api/v1/timecapsules")
                        .file(json)
                        .file(images)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(csrf())
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                principal, "test", principal.getAuthorities()))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // 400 상태 코드 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("요청에 유효하지 않은 인자입니다.")); // 에러 메시지 확인

    }

    @DisplayName("컨텐츠가 null일 경우 유효하지 않은 인자 예외를 반환한다.")
    @Test
    @MockCustomUser
    void 컨텐츠_NULL_예외_테스트() throws Exception {
        // given
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        TimecapsuleRequestDto dto = new TimecapsuleRequestDto("test@mail.com", null, true); // content를 null로 설정
        MockMultipartFile json = new MockMultipartFile(
                "request",
                "",
                "application/json",
                new ObjectMapper().writeValueAsString(dto).getBytes()
        );

        MockMultipartFile images = new MockMultipartFile(
                "images",
                "image.jpg",
                "image/jpeg",
                "test-image".getBytes()
        );

        // when
        doThrow(new BusinessException(ErrorCode.INVALID_ARGUMENT))
                .when(timecapsuleService).createTimecapsule(argThat(argument ->
                        argument.content() == null), anyList(), any());

        // then
        mockMvc.perform(multipart("/api/v1/timecapsules")
                        .file(json)
                        .file(images)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(csrf())
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                principal, "test", principal.getAuthorities()))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // 400 상태 코드 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("요청에 유효하지 않은 인자입니다.")); // 에러 메시지 확인
    }
}
