package com.jnu.festival.domain.bookmark.controller;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PartnerBookmarkController.class)
//@ExtendWith(MockitoExtension.class)
class PartnerBookmarkControllerTest {
////    @InjectMocks
////    private PartnerBookmarkController partnerBookmarkController;
//
//
////    @Mock
//    @MockBean
//    private PartnerBookmarkService partnerBookmarkService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
////        mockMvc = MockMvcBuilders
////                .standaloneSetup(partnerBookmarkController)
////                .setControllerAdvice(new GlobalExceptionHandler())
////                .build();
//    }
//
//    @Test
//    @WithMockUser
//    void 제휴업체_즐겨찾기_성공() throws Exception {
//        // given
//        Long partnerId = 1L;
//
//        // when & then
//        mockMvc.perform(post("/api/v1/bookmarks/partners/{partnerId}", partnerId)
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.success").value(true))
//                .andDo(print());
//
//    }
//
//    @Test
//    @WithMockUser
//    void partnerId가_잘못된_타입인_경우_제휴업체_즐겨찾기_실패() throws Exception {
//        // given
//        String partnerId = "wrong_type";
//
//        // when & then
//        mockMvc.perform(post("/api/v1/bookmarks/partners/{partnerId}", partnerId)
//                        .with(csrf()))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.error.message").value("유효하지 않은 인자 형식입니다."))
//                .andDo(print());
//
//    }
}
