package com.jnu.festival.domain.zone.controller;

import com.jnu.festival.domain.zone.dto.response.ZoneListDto;
import com.jnu.festival.domain.zone.service.ZoneService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ZoneController.class)   // 2
//@ExtendWith(MockitoExtension.class)   // 1
class ZoneControllerTest {
    @Autowired  // 2
    private MockMvc mockMvc;

//    @Mock // 1
    @MockBean   // 2
    private ZoneService zoneService;

//    @InjectMocks    // 1
//    private ZoneController zoneController;    // 1

    @BeforeEach
    void setUp() {
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(zoneController)
//                .setControllerAdvice(new GlobalExceptionHandler())
//                .build(); // 1
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @WithMockUser   // 1
    void location에_따른_zone_데이터_부재_시_zone_목록_조회_성공() throws Exception {
        // given
        List<ZoneListDto> zoneListDtos = new ArrayList<>();

        given(zoneService.readZoneList("stadium")).willReturn(zoneListDtos);

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/zones")
                .param("location", "stadium"));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(0))
                .andDo(print());
    }

    @Test
    @WithMockUser   // 2
    void location에_따른_zone_데이터_존재_시_zone_목록_조회_성공() throws Exception {
        // given
        List<ZoneListDto> response = new ArrayList<>();
        response.add(ZoneListDto.builder()
                .id(1L)
                .name("Main Stadium")
                .description("The main stadium for large events.")
                .build());

        response.add(ZoneListDto.builder()
                .id(2L)
                .name("Sports Field")
                .description("Field for sports activities.")
                .build());

        response.add(ZoneListDto.builder()
                .id(3L)
                .name("VIP Stadium")
                .description("VIP section in the stadium.")
                .build());

        response.add(ZoneListDto.builder()
                .id(4L)
                .name("Festival Stadium")
                .description("Stadium used for festival activities.")
                .build());

        given(zoneService.readZoneList("stadium")).willReturn(response);

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/zones")
                .param("location", "stadium"));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(4))
                .andDo(print());

    }

    @Test
    @WithMockUser   // 2
    void location이_null인_경우_zone_목록_조회_실패() throws Exception {
        // given

        // when
        ResultActions result = mockMvc.perform(get("/api/v1/zones")
                .param("location", (String) null));

        // then
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.message").value("필수 파라미터가 누락되었습니다."))
                .andDo(print());

    }

//    @Test
//    @WithMockUser   // 2
//    void 유효하지_않은_location인_경우_zone_목록_조회_실패() throws Exception {
//        // given
//        String invalidLocation = "invalid_location";
//
//        given(zoneService.readZoneList(invalidLocation)).willThrow(new BusinessException(ErrorCode.INVALID_LOCATION));
//
//        // when
//        ResultActions result = mockMvc.perform(get("/api/v1/zones")
//                .param("location", invalidLocation));
//
//        // then
//        result.andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.error.message").value("유효하지 않은 장소입니다."))
//                .andDo(print());
//    }
}