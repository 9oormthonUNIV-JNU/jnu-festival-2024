package com.jnu.festival.domain.partner.repository;

import com.jnu.festival.domain.partner.entity.Partner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartnerRepositoryTest {

    @Mock
    private PartnerRepository partnerRepository;

    @InjectMocks
    private Partner partner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAndFindPartner() {
        // given
        Partner partner = Partner.builder()
                .name("Test Partner")
                .location("Seoul")
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31))
                .description("This is a test partner.")
                .build();

        // when
        when(partnerRepository.save(partner)).thenReturn(partner);
        when(partnerRepository.findById(partner.getId())).thenReturn(Optional.of(partner));

        // then
        Partner savedPartner = partnerRepository.save(partner);
        assertThat(savedPartner).isNotNull();
        assertThat(savedPartner.getName()).isEqualTo("Test Partner");

        Partner foundPartner = partnerRepository.findById(partner.getId()).orElse(null);
        assertThat(foundPartner).isNotNull();
        assertThat(foundPartner.getName()).isEqualTo("Test Partner");
    }
}