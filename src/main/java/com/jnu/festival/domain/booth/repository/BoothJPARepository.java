package com.jnu.festival.domain.booth.repository;

import com.jnu.festival.domain.booth.entity.Booth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoothJPARepository extends JpaRepository<Booth, Long> {

    @Query("SELECT b FROM Booth b WHERE "
            + "(:location IS NULL OR b.location = :location) AND "
            + "(:period IS NULL OR b.period = :period) AND "
            + "(:category IS NULL OR b.category = :category)")
    List<Booth> findBooths(@Param("location") String location,
                           @Param("period") String period,
                           @Param("category") String category);

}
