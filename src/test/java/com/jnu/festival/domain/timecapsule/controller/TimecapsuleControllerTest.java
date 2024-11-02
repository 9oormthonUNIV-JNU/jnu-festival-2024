package com.jnu.festival.domain.timecapsule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.festival.global.utils.MockCustomUser;
import com.jnu.festival.domain.timecapsule.dto.request.TimecapsuleRequestDto;
import com.jnu.festival.domain.timecapsule.dto.response.TimecapsuleDto;
import com.jnu.festival.domain.timecapsule.dto.response.TimecapsuleListDto;
import com.jnu.festival.domain.timecapsule.repository.TimecapsuleImageRepository;
import com.jnu.festival.domain.timecapsule.repository.TimecapsuleRepository;
import com.jnu.festival.domain.timecapsule.service.TimecapsuleService;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.UserDetailsImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = TimecapsuleController.class)
@ExtendWith(MockitoExtension.class)
public class TimecapsuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TimecapsuleRepository timecapsuleRepository;

    @Mock
    private TimecapsuleImageRepository timecapsuleImageRepository;

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

    @DisplayName("로그인하지 않은 사용자가 요청을 보낼 경우 401 Unauthorized를 반환한다.")
    @Test
    void 비로그인_사용자_타임캡슐_등록_요청_테스트() throws Exception {
        // given
        TimecapsuleRequestDto dto = new TimecapsuleRequestDto("test@mail.com", "test content", true);
        MockMultipartFile json = new MockMultipartFile(
                "request",
                "",
                "application/json",
                new ObjectMapper().writeValueAsString(dto).getBytes()
        );

        // 이미지가 없는 요청이므로 null 설정
        MockMultipartFile images = new MockMultipartFile(
                "images",
                "",
                "image/jpeg",
                "".getBytes()
        );

        // when, then
        mockMvc.perform(multipart("/api/v1/timecapsules")
                        .file(json)
                        .file(images)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(csrf()))  // 인증 없이 요청
                .andExpect(MockMvcResultMatchers.status().isUnauthorized()); // 401 상태 코드 확인
    }

    @DisplayName("로그인한 사용자가 타임캡슐 목록을 요청하면 자신의 타임캡슐이 위쪽에 오고, 이후에 공개 타임캡슐이 반환된다.")
    @Test
    @MockCustomUser
    void getTimecapsuleList_인증된_사용자() throws Exception {
        // given
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User actualUser = principal.getUser();

        List<TimecapsuleDto> myTimecapsules = List.of(
                new TimecapsuleDto(1L, "test", "test", List.of("image1", "image2"), LocalDateTime.now()));
        List<TimecapsuleDto> publicTimecapsules = List.of(
                new TimecapsuleDto(2L, "aaaa", "test", List.of("image1", "image2"), LocalDateTime.now()));

        TimecapsuleListDto expectedResponse = TimecapsuleListDto.builder()
                .myTimecapsules(myTimecapsules)
                .timecapsules(publicTimecapsules)
                .build();

        // when
        when(timecapsuleService.getTimecapsuleList(principal)).thenReturn(expectedResponse);

        // then
        mockMvc.perform(get("/api/v1/timecapsules")
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                principal, "test", principal.getAuthorities())))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.my_timecapsules").isNotEmpty())  // 본인 타임캡슐 확인
                .andExpect(jsonPath("$.data.timecapsules").isNotEmpty());  // 공개 타임캡슐 확인

        // 서비스 메서드 호출 검증
        verify(timecapsuleService).getTimecapsuleList(principal);
    }

    @DisplayName("로그인하지 않은 사용자가 타임캡슐 목록을 요청하면 공개 타임캡슐 목록만 포함된 JSON 응답이 반환된다.")
    @Disabled
    @Test
    void getTimecapsuleList_비로그인_사용자() throws Exception {
        // given
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        List<TimecapsuleDto> publicTimecapsules = List.of(
                new TimecapsuleDto(2L, "aaaa", "test", List.of("image1", "image2"), LocalDateTime.now()));

        TimecapsuleListDto expectedResponse = TimecapsuleListDto.builder()
                .timecapsules(publicTimecapsules)
                .build();

        // Mock 서비스 레이어 메서드
        when(timecapsuleService.getTimecapsuleList(principal)).thenReturn(expectedResponse);

        // when, then
        mockMvc.perform(get("/api/v1/timecapsules")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.my_timecapsules").isEmpty())
                .andExpect(jsonPath("$.data.timecapsules").isNotEmpty())
                .andExpect(jsonPath("$.error").doesNotExist());

        // 서비스 메서드 호출 검증
        verify(timecapsuleService).getTimecapsuleList(null);
    }

    @DisplayName("타임캡슐 삭제")
    @Test
    @MockCustomUser
    void deleteTimecapsule_성공() throws Exception {
        //given
        Long timecapsuleId = 1L;
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User actualUser = principal.getUser();

        //when, then
        mockMvc.perform(delete("/api/v1/timecapsules/{timecapsuleId}", timecapsuleId)
                .with((authentication(new UsernamePasswordAuthenticationToken(
                        principal, "test", principal.getAuthorities()))))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isEmpty());

        // verify
        verify(timecapsuleService).deleteTimecapsule(eq(timecapsuleId), eq(principal));
    }

    @DisplayName("없는 타임캡슐을 지우려고했을 때 NOT_FOUND_TIMECAPSULE을 예외처리하는지 테스트")
    @Test
    @MockCustomUser
    void deleteTimecapsule_NotFound() throws Exception {
        // given
        Long timecapsuleId = 999L; // 존재하지 않는 ID
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        // when
        doThrow(new BusinessException(ErrorCode.NOT_FOUND_TIMECAPSULE))
                .when(timecapsuleService).deleteTimecapsule(timecapsuleId, principal);

        // then
        mockMvc.perform(delete("/api/v1/timecapsules/{timecapsuleId}", timecapsuleId)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.message").value("존재하지 않는 타임캡슐입니다.")); // 에러 메시지에 맞춰 확인

        // 서비스 메서드 호출 확인
        verify(timecapsuleService).deleteTimecapsule(anyLong(), any());
    }

    @Test
    @MockCustomUser
    void deleteTimecapsule_NotMatchUser() throws Exception{
        //given
        Long timecapsuleId = 1L;
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User actualUser = principal.getUser();

        //when
        doThrow(new BusinessException(ErrorCode.NOT_MATCH_USER))
                .when(timecapsuleService).deleteTimecapsule(timecapsuleId, principal);

        //then
        mockMvc.perform(delete("/api/v1/timecapsules/{timecapsuleId}", timecapsuleId)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.message").value("해당 사용자가 일치하지 않습니다."));
    }


}
