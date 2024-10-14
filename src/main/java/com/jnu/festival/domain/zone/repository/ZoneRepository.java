package com.jnu.festival.domain.zone.repository;

import com.jnu.festival.domain.common.Location;
import com.jnu.festival.domain.zone.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone,Long> {
    @Query("select z from Zone as z where (:location is null or z.location = :location)")
    List<Zone> findAllByLocation(Location location);
}
