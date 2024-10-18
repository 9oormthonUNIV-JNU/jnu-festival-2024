package com.jnu.festival.domain.booth.repository;

import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.entity.BoothImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoothImageRepository extends JpaRepository<BoothImage, Long> {
    List<BoothImage> findAllByBooth(Booth booth);
}
