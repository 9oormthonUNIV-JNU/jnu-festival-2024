package com.jnu.festival.domain.booth.repository;

import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.entity.BoothImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoothImageRepository extends JpaRepository<BoothImage, Long> {
    List<BoothImage> findAllByBooth(Booth booth);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from BoothImage as bi where bi.booth = :booth")
    void deleteAllByBooth(Booth booth);
}
