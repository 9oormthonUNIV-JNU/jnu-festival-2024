package com.jnu.festival.domain.bookmark.service;

import com.jnu.festival.domain.bookmark.entity.PartnerBookmark;
import com.jnu.festival.domain.bookmark.repository.PartnerBookmarkRepository;
import com.jnu.festival.domain.partner.repository.PartnerRepository;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.user.entity.Role;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.auth.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartnerBookmarkServiceTest {

    @InjectMocks
    private PartnerBookmarkService partnerBookmarkService;

    @Mock
    private PartnerBookmarkRepository partnerBookmarkRepository;

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    UserDetails userDetails;

    @DisplayName("파트너북마크 생성")
    @WithMockUser
    @Test
    void createPartnerBookmark() throws Exception{
        //given

        User user1 = User.builder()
                .nickname("testUser")
                .password("1234")
                .role(Role.ROLE_USER)
                .build();

        Partner partner1 = Partner.builder()
                .name("Test Partner")
                .location("Seoul")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .description("This is a test partner.")
                .build();

        PartnerBookmark partnerBookmark1 = PartnerBookmark.builder()
                .user(user1)
                .partner(partner1)
                .isDeleted(false)
                .build();
        lenient().when(userDetails.getUsername()).thenReturn("testUser");
        lenient().when(userRepository.findByNickname("testUser")).thenReturn(Optional.of(user1));
        given(partnerRepository.findById(partner1.getId())).willReturn(Optional.of(partner1));
        given(partnerBookmarkRepository.findByUserAndPartner(user1, partner1)).willReturn(Optional.of(partnerBookmark1));
        lenient().when(partnerBookmarkRepository.save(any(PartnerBookmark.class))).thenReturn(partnerBookmark1);

        //when
        partnerBookmarkService.createPartnerBookmark(partner1.getId(),new UserDetailsImpl(user1));
        PartnerBookmark foundBookmark = partnerBookmarkRepository.findByUserAndPartner(user1,partner1).orElse(null);

        //then
        assertEquals(partnerBookmark1,foundBookmark);
    }

    @DisplayName("파트너북마크 취소")
    @Test
    @WithMockUser
    void deletePartnerBookmark() throws Exception{
        //given
        User user1 = User.builder()
                .nickname("testUser")
                .password("1234")
                .role(Role.ROLE_USER)
                .build();

        Partner partner1 = Partner.builder()
                .name("Test Partner")
                .location("Seoul")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .description("This is a test partner.")
                .build();

        PartnerBookmark partnerBookmark1 = PartnerBookmark.builder()
                .user(user1)
                .partner(partner1)
                .isDeleted(false)
                .build();
        when(userRepository.findByNickname(user1.getNickname())).thenReturn(Optional.of(user1));
        when(partnerRepository.findById(partner1.getId())).thenReturn(Optional.of(partner1));
        when(partnerBookmarkRepository.findByUserAndPartner(user1, partner1)).thenReturn(Optional.of(partnerBookmark1));

        // when
        partnerBookmarkService.deletePartnerBookmark(partner1.getId(), new UserDetailsImpl(user1));

        // then
        verify(partnerBookmarkRepository, times(1)).delete(partnerBookmark1);

    }

    @DisplayName("유효하지 않은 partnerId로 북마크 생성 시 예외 발생")
    @Test
    void createPartnerBookmarkWithInvalidPartnerId() {
        // given
        User user = User.builder()
                .nickname("testUser")
                .password("1234")
                .role(Role.ROLE_USER)
                .build();



        when(userRepository.findByNickname("testUser")).thenReturn(Optional.of(user));
        when(partnerRepository.findById(999L)).thenReturn(Optional.empty()); // 유효하지 않은 partnerId

        // when&then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            partnerBookmarkService.createPartnerBookmark(999L, new UserDetailsImpl(user));
        });

        assertEquals(ErrorCode.NOT_FOUND_PARTNER, exception.getErrorCode());
    }

    @DisplayName("유효하지 않은 user로 북마크 생성 시 예외 발생")
    @Test
    void createPartnerBookmarkWithInvalidUser() {
        // given
        Partner partner1 = Partner.builder()
                .name("Test Partner")
                .location("Seoul")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .description("This is a test partner.")
                .build();


        when(userRepository.findByNickname("invalidUser")).thenReturn(Optional.empty()); // 유효하지 않은 사용자
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(userDetails.getUsername()).thenReturn("invalidUser");

        // when / then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            partnerBookmarkService.createPartnerBookmark(partner1.getId(), userDetails);
        });

        assertEquals(ErrorCode.NOT_FOUND_USER, exception.getErrorCode());
    }

    @DisplayName("유효하지 않은 사용자로 북마크 삭제 시 예외 발생")
    @Test
    void deletePartnerBookmarkWithInvalidUser() {
        // given
        Partner partner1 = Partner.builder()
                .name("Test Partner")
                .location("Seoul")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .description("This is a test partner.")
                .build();

        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(userDetails.getUsername()).thenReturn("invalidUser");


        when(userRepository.findByNickname("invalidUser")).thenReturn(Optional.empty());

        // when&then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            partnerBookmarkService.deletePartnerBookmark(partner1.getId(), userDetails);
        });

        assertEquals(ErrorCode.NOT_FOUND_USER, exception.getErrorCode());
    }
    @DisplayName("유효하지 않은 partnerId로 북마크 삭제 시 예외 발생")
    @Test
    void deletePartnerBookmarkWithInvalidPartnerId() {
        // given
        Long partnerId = 999L;
        User user = User.builder()
                .nickname("testUser")
                .password("1234")
                .role(Role.ROLE_USER)
                .build();

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        when(userRepository.findByNickname("testUser")).thenReturn(Optional.of(user));
        when(partnerRepository.findById(partnerId)).thenReturn(Optional.empty());

        // when&then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            partnerBookmarkService.deletePartnerBookmark(partnerId, userDetails);
        });

        assertEquals(ErrorCode.NOT_FOUND_PARTNER, exception.getErrorCode());
    }

    @DisplayName("유효하지 않은 파트너 북마크로 북마크 삭제 시 예외 발생")
    @Test
    void deletePartnerBookmarkWithInvalidPartnerBookmark() {
        // given
        Partner partner1 = Partner.builder()
                .name("Test Partner")
                .location("Seoul")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .description("This is a test partner.")
                .build();
        User user1 = User.builder()
                .nickname("testUser")
                .password("1234")
                .role(Role.ROLE_USER)
                .build();

        UserDetailsImpl userDetails = new UserDetailsImpl(user1);

        // mock behavior
        when(userRepository.findByNickname("testUser")).thenReturn(Optional.of(user1));
        when(partnerRepository.findById(partner1.getId())).thenReturn(Optional.of(partner1)); // 유효한 partner
        when(partnerBookmarkRepository.findByUserAndPartner(user1, partner1)).thenReturn(Optional.empty()); // 유효하지 않은 partnerBookmark

        // when / then
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            partnerBookmarkService.deletePartnerBookmark(partner1.getId(), userDetails);
        });

        assertEquals(ErrorCode.NOT_FOUND_PARTNERBOOKMARK, exception.getErrorCode());
    }

}