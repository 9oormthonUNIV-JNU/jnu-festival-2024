package com.jnu.festival.domain.timecapsule.repository;

import com.jnu.festival.domain.timecapsule.entity.Timecapsule;
import com.jnu.festival.domain.timecapsule.entity.TimecapsuleImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimecapsuleImageRepository extends JpaRepository<TimecapsuleImage, Long> {
    List<TimecapsuleImage> findAllByTimecapsule(Timecapsule timecapsule);
}
