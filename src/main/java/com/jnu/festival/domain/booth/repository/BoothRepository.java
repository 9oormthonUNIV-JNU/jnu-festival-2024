package com.jnu.festival.domain.booth.repository;

import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.entity.BoothCategory;
import com.jnu.festival.domain.booth.entity.Period;
import com.jnu.festival.domain.common.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoothRepository extends JpaRepository<Booth, Long> {
    @Query("select b from Booth as b where (:location is null or b.location = :location) and (:category is null or b.category = :category) and (:period is null or b.period = :period)")
    List<Booth> findAllByLocationAndAndCategoryAndPeriod(@Param("location") Location location,
                                                         @Param("category") BoothCategory category,
                                                         @Param("period") Period period);

    List<Booth> findAllByNameContains(String keyword);
}
