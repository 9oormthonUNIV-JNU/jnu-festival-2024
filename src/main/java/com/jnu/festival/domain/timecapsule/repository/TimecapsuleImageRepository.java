package com.jnu.festival.domain.timecapsule.repository;

import com.jnu.festival.domain.timecapsule.entity.Timecapsule;
import com.jnu.festival.domain.timecapsule.entity.TimecapsuleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimecapsuleImageRepository extends JpaRepository<TimecapsuleImage, Long> {
    List<TimecapsuleImage> findAllByTimecapsule(Timecapsule timecapsule);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from TimecapsuleImage as ti where ti.timecapsule = :timecapsule")
    void deleteAllByTimecapsule(Timecapsule timecapsule);
}
