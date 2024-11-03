//package com.jnu.festival.domain.partner.service;
//
//import com.jnu.festival.domain.bookmark.repository.PartnerBookmarkRepository;
//import com.jnu.festival.domain.partner.DTO.PartnerDto;
//import com.jnu.festival.domain.partner.DTO.PartnerListDto;
//import com.jnu.festival.domain.partner.repository.PartnerImageRepository;
//import com.jnu.festival.domain.partner.repository.PartnerRepository;
//import com.jnu.festival.domain.partner.entity.Partner;
//import com.jnu.festival.domain.user.repository.UserRepository;
//import com.jnu.festival.global.error.exception.BusinessException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.test.context.support.WithMockUser;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.junit.jupiter.api.Assertions.*;
//@ExtendWith(MockitoExtension.class)
//class PartnerServiceTest {
//
//    @InjectMocks
//    private PartnerService partnerService;
//
//    @Mock
//    private PartnerRepository partnerRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PartnerBookmarkRepository partnerBookmarkRepository;
//
//    @Mock
//    private PartnerImageRepository partnerImageRepository;
//
//    @DisplayName("파트너 전체조회")
//    @Test
//    @WithMockUser(username = "testUser")
//    void readPartnerList() throws Exception {
//        //given
//        Partner partner1 = Partner.builder()
//                .name("Test Partner")
//                .location("Seoul")
//                .startDate(LocalDate.of(2024, 1, 1))
//                .endDate(LocalDate.of(2024, 12, 31))
//                .description("This is a test partner.")
//                .build();
//        Partner partner2 = Partner.builder()
//                .name("Test Partner")
//                .location("Seoul")
//                .startDate(LocalDate.of(2024, 1, 1))
//                .endDate(LocalDate.of(2024, 12, 31))
//                .description("This is a test partner.")
//                .build();
//
//        List<Partner> partners = Arrays.asList(partner1,partner2);
//
//        given(partnerRepository.findAll()).willReturn(partners);
////        given(userRepository.findByNickname("testUser")).willReturn(Optional.of(new User("testUser","1234", Role.ROLE_USER)));
//
//
//        //when
//        List<PartnerListDto> result = partnerService.readPartnerList(null);
//        //then
//        assertEquals(partners.size(),result.size());
//
//    }
//    @DisplayName("파트너 상세조회")
//    @Test
//    @WithMockUser(username = "testUser")
//    void readPartnerByPartnerId() {
//        //given
//        Partner partner1 = Partner.builder()
//                .name("Test Partner")
//                .location("Seoul")
//                .startDate(LocalDate.of(2024, 1, 1))
//                .endDate(LocalDate.of(2024, 12, 31))
//                .description("This is a test partner.")
//                .build();
//        Partner partner2 = Partner.builder()
//                .name("Test Partner")
//                .location("Seoul")
//                .startDate(LocalDate.of(2024, 1, 1))
//                .endDate(LocalDate.of(2024, 12, 31))
//                .description("This is a test partner.")
//                .build();
//
//        Long partnerId = 1L;
//        given(partnerRepository.findById(partnerId)).willReturn(Optional.of(partner1));
//
//
//        //when
//        PartnerDto result =  partnerService.readPartner(partnerId,null);
//
//        //then
//        assertEquals(result.getName(),partner1.getName());
//    }
//
//    //유효하지않은 partnerId로 조회
//    @Test
//    @WithMockUser(username = "testUser")
//    void readPartnerByPartnerId_Fail() throws Exception {
//        //given
//        Partner partner1 = Partner.builder()
//                .name("Test Partner")
//                .location("Seoul")
//                .startDate(LocalDate.of(2024, 1, 1))
//                .endDate(LocalDate.of(2024, 12, 31))
//                .description("This is a test partner.")
//                .build();
//        Partner partner2 = Partner.builder()
//                .name("Test Partner")
//                .location("Seoul")
//                .startDate(LocalDate.of(2024, 1, 1))
//                .endDate(LocalDate.of(2024, 12, 31))
//                .description("This is a test partner.")
//                .build();
//
//        Long partnerId = 1000L;
//        given(partnerRepository.findById(partnerId)).willReturn(Optional.empty());
//
//        //when
//        assertThrows(BusinessException.class, () ->{
//            partnerService.readPartner(partnerId,null);
//        });
//
//    }
//
//
//}