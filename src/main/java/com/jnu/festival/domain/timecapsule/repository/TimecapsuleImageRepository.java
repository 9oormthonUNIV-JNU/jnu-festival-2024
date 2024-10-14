package com.jnu.festival.domain.timeCapsule.repository;

import com.jnu.festival.domain.timeCapsule.entity.TimeCapsule;
import com.jnu.festival.domain.timeCapsule.entity.TimeCapsuleImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeCapsuleImageRepository extends JpaRepository<TimeCapsuleImage, Long> {

    List<TimeCapsuleImage> findTimeCapsuleImagesByTimeCapsule(TimeCapsule timecapsule);
}
