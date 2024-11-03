package com.jnu.festival.domain.partner.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PartnerController.class)
@ExtendWith(MockitoExtension.class)
class PartnerControllerTest {

////    @InjectMocks
////    private PartnerController partnerController;
//
////    @Mock
//    @MockBean
//    private PartnerService partnerService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
////        mockMvc = MockMvcBuilders
////                .standaloneSetup(partnerController)
////                .setControllerAdvice(new GlobalExceptionHandler()) // 여기에 @ControllerAdvice 추가
////                .build();
//    }
//
//    @Test
//    @WithMockUser
//    void 제휴업체_데이터_부재_시_제휴업체_목록_조회_성공() throws Exception {
//        // given
//        List<PartnerListDto> response = new ArrayList<>();
//
//        given(partnerService.readPartnerList(any(UserDetailsImpl.class))).willReturn(response);
//
//        // when
//        ResultActions result = mockMvc.perform(get("/api/v1/partners"));
//
//        // then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.length()").value(0))
//                .andDo(print());
//    }
//
//    @Test
//    @WithMockUser
//    void 제휴업체_데이터_존재_시_제휴업체_목록_조회_성공() throws Exception {
//        // given
//        List<PartnerListDto> response = new ArrayList<>();
//
//        response.add(PartnerListDto.builder()
//                .id(1L)
//                .name("Partner One")
//                .description("Description for Partner One")
//                .bookmark(true)
//                .createdAt(LocalDateTime.now().minusDays(5))
//                .build());
//
//        response.add(PartnerListDto.builder()
//                .id(2L)
//                .name("Partner Two")
//                .description("Description for Partner Two")
//                .bookmark(false)
//                .createdAt(LocalDateTime.now().minusDays(3))
//                .build());
//
//        response.add(PartnerListDto.builder()
//                .id(3L)
//                .name("Partner Three")
//                .description("Description for Partner Three")
//                .bookmark(true)
//                .createdAt(LocalDateTime.now().minusDays(10))
//                .build());
//
//        response.add(PartnerListDto.builder()
//                .id(4L)
//                .name("Partner Four")
//                .description("Description for Partner Four")
//                .bookmark(false)
//                .createdAt(LocalDateTime.now().minusDays(1))
//                .build());
//
//        response.add(PartnerListDto.builder()
//                .id(5L)
//                .name("Partner Five")
//                .description("Description for Partner Five")
//                .bookmark(true)
//                .createdAt(LocalDateTime.now().minusHours(12))
//                .build());
//
//        given(partnerService.readPartnerList(any(UserDetailsImpl.class))).willReturn(response);
//
//        // when
//        ResultActions result = mockMvc.perform(get("/api/v1/partners"));
//
//        // then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.length()").exists())
//                .andDo(print());
//    }
//
//    @Test
//    void 올바른_타입의_partnerId인_경우_제휴업체_상세_조회_성공() throws Exception {
//        // given
//        Long parterId = 1L;
//
//        PartnerDto partnerDto = PartnerDto.builder()
//                .id(1L)
//                .name("Sample Partner")
//                .description("This is a sample partner description.")
//                .images(List.of("image1.jpg", "image2.jpg"))
//                .location("Seoul, Korea")
//                .startDate(LocalDate.of(2024, 1, 1))
//                .endDate(LocalDate.of(2024, 12, 31))
//                .bookmark(true)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        given(partnerService.readPartner(eq(parterId), any(UserDetailsImpl.class))).willReturn(partnerDto);
//
//        // when
//        ResultActions result = mockMvc.perform(get("/api/v1/partners/{partnerId}", parterId));
//
//        // then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.id").value(1))
//                .andExpect(jsonPath("$.data.name").value("Sample Partner"))
//                .andDo(print());
//    }
//
//    @Test
//    void 올바르지_않은_타입의_partnerId인_경우_제휴업체_상세_조회_성공() throws Exception {
//        // given
//        String parterId = "wrong_partnerId";
//
//        PartnerDto partnerDto = PartnerDto.builder()
//                .id(1L)
//                .name("Sample Partner")
//                .description("This is a sample partner description.")
//                .images(List.of("image1.jpg", "image2.jpg"))
//                .location("Seoul, Korea")
//                .startDate(LocalDate.of(2024, 1, 1))
//                .endDate(LocalDate.of(2024, 12, 31))
//                .bookmark(true)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        // when
//        ResultActions result = mockMvc.perform(get("/api/v1/partners/{partnerId}", parterId));
//
//        // then
//        result.andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.error.message").value("유효하지 않은 인자 형식입니다."))
//                .andDo(print());
//    }
}