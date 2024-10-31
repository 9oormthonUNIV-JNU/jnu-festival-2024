package com.jnu.festival.api.domain.booth.repository;


import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.entity.BoothCategory;
import com.jnu.festival.domain.booth.entity.Period;
import com.jnu.festival.domain.booth.repository.BoothRepository;
import com.jnu.festival.domain.common.Location;
import org.assertj.core.api.Assertions;
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
public class BoothRepositoryTest {

    //1. 실제 데이터베이스를 모방하기 위해 H2 db를 사용할 것
    // 실제 db를 사용하는 것은 비추
    // 2. 실제 레포지토리를 불러와야함
    @Autowired
    private BoothRepository boothRepository;

    @BeforeEach
    void Init() {
        //Arrange
        Booth booth = Booth.builder()
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

        Booth booth2 = Booth.builder()
                .name("부스이름2")
                .location(Location.BACKGATE_STREET)
                .index(1)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(18, 0))
                .description("설명입니다")
                .category(BoothCategory.FOOD)
                .period(Period.ALLTIME)
                .build();

        //Act
        //먼저 h2에 저장
        boothRepository.save(booth);
        boothRepository.save(booth2);
    }

    //단위테스트 이름 지정 -> 일관성이 있어야 한다!
    // 테스트 메소드 이름 구분시 underscore 꼭 넣을것
    @Test
    public void BoothRepository_findAllByLocationAndAndCategoryAndPeriod_ReturnsBoothList() {
        //실제 JPA 내부 저장소 내부를 체크하는 것이 아님

        List<Booth> returnedBooth = boothRepository.findAllByLocationAndAndCategoryAndPeriod(Location.BACKGATE_STREET, BoothCategory.FOOD, Period.ALLTIME);

        //Assert
        Assertions.assertThat(returnedBooth).isNotNull();
        Assertions.assertThat(returnedBooth).hasSize(1);  // booth2만 조회됨을 확인
        Assertions.assertThat(returnedBooth.get(0).getName()).isEqualTo("부스이름2");
    }


    @Test
    public void BoothRepository_FindAllByNameContains_ReturnsBoothList() {
        //Act
        List<Booth> returnedBooth = boothRepository.findAllByNameContains("부");


        //Assert
        Assertions.assertThat(returnedBooth).size().isEqualTo(2);

    }

}
