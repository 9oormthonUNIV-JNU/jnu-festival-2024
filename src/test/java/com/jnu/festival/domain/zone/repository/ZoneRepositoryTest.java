package com.jnu.festival.domain.zone.repository;

import com.jnu.festival.global.common.Location;
import com.jnu.festival.domain.zone.entity.Zone;
import com.jnu.festival.global.config.JpaAuditingConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaAuditingConfig.class)
class ZoneRepositoryTest {
//    @Autowired
//    private ZoneRepository zoneRepository;
//
//    private List<Zone> zones;
//
//    @BeforeEach
//    void setUp() {
//        zones = new ArrayList<>();
//
//        zones.add(Zone.builder()
//                .name("Main Stadium")
//                .location(Location.STADIUM)
//                .description("The main stadium for large events.")
//                .build());
//
//        zones.add(Zone.builder()
//                .name("Concert Square")
//                .location(Location.SQUARE_518)
//                .description("A large square used for concerts.")
//                .build());
//
//        zones.add(Zone.builder()
//                .name("Food Street")
//                .location(Location.BACKGATE_STREET)
//                .description("A street with a variety of food stalls.")
//                .build());
//
//        zones.add(Zone.builder()
//                .name("Sports Field")
//                .location(Location.STADIUM)
//                .description("Field for sports activities.")
//                .build());
//
//        zones.add(Zone.builder()
//                .name("Exhibition Plaza")
//                .location(Location.SQUARE_518)
//                .description("Area for exhibitions and events.")
//                .build());
//
//        zones.add(Zone.builder()
//                .name("Street Market")
//                .location(Location.BACKGATE_STREET)
//                .description("A market street with various shops.")
//                .build());
//
//        zones.add(Zone.builder()
//                .name("VIP Stadium")
//                .location(Location.STADIUM)
//                .description("VIP section in the stadium.")
//                .build());
//
//        zones.add(Zone.builder()
//                .name("Event Square")
//                .location(Location.SQUARE_518)
//                .description("Square for holding large-scale events.")
//                .build());
//
//        zones.add(Zone.builder()
//                .name("Snack Alley")
//                .location(Location.BACKGATE_STREET)
//                .description("Street with various snack shops.")
//                .build());
//
//        zones.add(Zone.builder()
//                .name("Festival Stadium")
//                .location(Location.STADIUM)
//                .description("Stadium used for festival activities.")
//                .build());
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void location에_따른_zone_데이터_부재_시_zone_목록_조회_성공() {
//        // given
//
//        // when
//        List<Zone> zones = zoneRepository.findAllByLocation(Location.from("stadium"));
//
//        // then
//        assertThat(zones).isEmpty();
//    }
//
//    @Test
//    void location에_따른_zone_데이터_존재_시_zone_목록_조회_성공() {
//        // given
//        zoneRepository.saveAll(zones);
//
//        // when
//        List<Zone> zones = zoneRepository.findAllByLocation(Location.from("stadium"));
//
//        // then
//        assertThat(zones.size()).isEqualTo(4);
//    }
}