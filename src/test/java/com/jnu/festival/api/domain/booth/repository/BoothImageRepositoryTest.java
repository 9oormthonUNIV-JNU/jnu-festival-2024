package com.jnu.festival.api.domain.booth.repository;

import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.entity.BoothCategory;
import com.jnu.festival.domain.booth.entity.BoothImage;
import com.jnu.festival.domain.booth.entity.Period;
import com.jnu.festival.domain.booth.repository.BoothImageRepository;
import com.jnu.festival.domain.booth.repository.BoothRepository;
import com.jnu.festival.domain.common.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class BoothImageRepositoryTest {
    @Autowired
    private BoothImageRepository boothImageRepository;

    @Autowired
    private BoothRepository boothRepository;

    private Booth booth;

    @BeforeEach
    void Init() {
        //Arrange
        booth = Booth.builder()
                .name("부스이름1")
                .location(Location.BACKGATE_STREET)
                .index(1)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(18, 0))
                .description("설명입니다")
                .category(BoothCategory.FOOD)
                .period(Period.NIGHTTIME)
                .build();

        BoothImage boothImage = BoothImage.builder()
                .url("https://example.com/images/boothB1.jpg")
                .booth(booth)
                .build();

        //먼저 h2에 저장
        boothRepository.save(booth);
        boothImageRepository.save(boothImage);
    }


    @Test
    public void BoothImageRepository_FindAllByBooth_ReturnBoothList() {
        List<String> returnedBoothImages = boothImageRepository.findAllByBooth(booth).stream()
                .map(BoothImage::getUrl)
                .toList();
    }

}
