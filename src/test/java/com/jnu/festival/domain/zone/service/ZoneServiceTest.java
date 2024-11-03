package com.jnu.festival.domain.zone.service;

import com.jnu.festival.global.common.Location;
import com.jnu.festival.domain.zone.dto.response.ZoneListDto;
import com.jnu.festival.domain.zone.entity.Zone;
import com.jnu.festival.domain.zone.repository.ZoneRepository;
import com.jnu.festival.global.error.exception.BusinessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ZoneServiceTest {
    @Mock
    private ZoneRepository zoneRepository;

    @InjectMocks
    private ZoneService zoneService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void location에_따른_zone_데이터_부재_시_zone_목록_조회_성공() {
        // given
        List<Zone> zones = new ArrayList<>();

        given(zoneRepository.findAllByLocation(Location.from("stadium"))).willReturn(zones);

        // when
        List<ZoneListDto> response = zoneService.readZoneList("stadium");

        // then
        assertThat(response).isEmpty();
    }

    @Test
    void location에_따른_zone_데이터_존재_시_zone_목록_조회_성공() {
        // given
        List<Zone> zones = new ArrayList<>();
        zones.add(Zone.builder()
                .name("Main Stadium")
                .location(Location.STADIUM)
                .description("The main stadium for large events.")
                .build());

        zones.add(Zone.builder()
                .name("VIP Stadium")
                .location(Location.STADIUM)
                .description("VIP section in the stadium.")
                .build());

        zones.add(Zone.builder()
                .name("Concert Stadium")
                .location(Location.STADIUM)
                .description("Stadium used for concerts and events.")
                .build());

        zones.add(Zone.builder()
                .name("Sports Stadium")
                .location(Location.STADIUM)
                .description("Stadium for various sports activities.")
                .build());

        zones.add(Zone.builder()
                .name("Festival Stadium")
                .location(Location.STADIUM)
                .description("Stadium used for festival activities.")
                .build());

        given(zoneRepository.findAllByLocation(Location.from("stadium"))).willReturn(zones);

        // when
        List<ZoneListDto> response = zoneService.readZoneList("stadium");

        // then
        assertThat(response.size()).isEqualTo(5);

    }

    @Test
    void 유효하지_않은_location인_경우_zone_목록_조회_실패() {
        // given
        String invalidLocation = "invalid_location";

        // when
        // then
        assertThatThrownBy(() -> zoneService.readZoneList(invalidLocation))
                .isInstanceOf(BusinessException.class)
                .hasMessage("유효하지 않은 장소입니다.");
    }
}