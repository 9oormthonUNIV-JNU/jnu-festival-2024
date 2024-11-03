package com.jnu.festival.domain.bookmark.repository;

import com.jnu.festival.domain.bookmark.entity.PartnerBookmark;
import com.jnu.festival.domain.partner.repository.PartnerRepository;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.user.entity.Role;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PartnerBookmarkRepositoryTest {

    @Autowired
    private PartnerBookmarkRepository partnerBookmarkRepository;


    User user = User.builder()
            .nickname("testUser")
            .password("1234")
            .role(Role.ROLE_USER)
            .build();

    Partner partner = Partner.builder()
            .name("Test Partner")
            .location("Seoul")
            .startDate(LocalDate.of(2024, 1, 1))
            .endDate(LocalDate.of(2024, 12, 31))
            .description("This is a test partner.")
            .build();
    @Autowired
    PartnerRepository partnerRepository;

    @Autowired
    UserRepository userRepository;






    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus status;

    @BeforeEach
    void beforeEach() {
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void afterEach() {
        transactionManager.rollback(status);
    }


    @DisplayName("파트너북마크 생성과 조회")
    @Test
    void saveAndFindPartnerBookmark() {
        //given
        Partner partner1 = partnerRepository.save(partner);
        User user1 = userRepository.save(user);

        PartnerBookmark partnerBookmark1 = PartnerBookmark.builder()
                .user(user1)
                .partner(partner1)
                .isDeleted(false)
                .build();


        //when
        partnerBookmarkRepository.save(partnerBookmark1);

        //then
        PartnerBookmark foundBookmark = partnerBookmarkRepository.findByUserAndPartner(user,partner).orElse(null);
        assertEquals(partnerBookmark1,foundBookmark);


    }

    @DisplayName("파트너북마크 취소")
    @Test
    void deletePartnerBookmark() {
        //given
        Partner partner1 = partnerRepository.save(partner);
        User user1 = userRepository.save(user);

        PartnerBookmark partnerBookmark1 = PartnerBookmark.builder()
                .user(user1)
                .partner(partner1)
                .isDeleted(false)
                .build();

        PartnerBookmark savedBookmark = partnerBookmarkRepository.save(partnerBookmark1);


        //when
        partnerBookmarkRepository.delete(savedBookmark);

        //then
        Optional<PartnerBookmark> deletedBookmark = partnerBookmarkRepository.findById(partnerBookmark1.getId());
        assertThat(deletedBookmark).isEmpty();


    }


}