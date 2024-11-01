package com.jnu.festival.domain.timecapsule.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.festival.domain.timecapsule.dto.request.TimecapsuleRequestDto;
import com.jnu.festival.global.security.config.SecurityConfig;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.function.RequestPredicates;
import software.amazon.awssdk.services.s3.endpoints.internal.Value.Str;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TimecapsuleServiceTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private HttpHeaders headers;
    private String token = "";

    private TimecapsuleRequestDto requestDto;

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @WithMockUser
    void 타임캡슐_이미지_제외_생성_테스트() throws Exception {
        headers.set("Authorization", token);
        // given
        final String filePath = "src/test/resources/testImage/profileImg.png";
        MockMultipartFile images = new MockMultipartFile("images", "profileImg.png", MediaType.MULTIPART_FORM_DATA_VALUE, "test-image-file-content-with-string".getBytes());
//        MockMultipartFile image2 = new MockMultipartFile("images", "profileImg.png", MediaType.MULTIPART_FORM_DATA_VALUE, "test-image-file-content-with-string".getBytes());

        TimecapsuleRequestDto timecapsuleRequestDto = new TimecapsuleRequestDto("test", "test", true);
        String jsonContent = objectMapper.writeValueAsString(timecapsuleRequestDto);

        MockMultipartFile request = new MockMultipartFile("request", "request.json", MediaType.APPLICATION_JSON_VALUE, jsonContent.getBytes(
                StandardCharsets.UTF_8));

        mockMvc.perform(
                        multipart("/api/v1/timecapsules")
                                .file(images)
//                                .file(image2)
                                .file(request)
                                .headers(headers)
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                ).andExpect(status().isOk())
                .andDo(print());

    }
}
