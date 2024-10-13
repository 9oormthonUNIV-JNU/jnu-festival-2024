package com.jnu.festival.domain.zone.Repository;

import com.jnu.festival.domain.zone.Entity.Location;
import com.jnu.festival.domain.zone.Entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone,Long> {
    List<Zone> findByLocation(Location location);
}
