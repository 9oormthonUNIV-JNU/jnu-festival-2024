package com.jnu.festival.domain.timeCapsule.repository;

import com.jnu.festival.domain.timeCapsule.entity.TimeCapsule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeCapsuleRepository extends JpaRepository<TimeCapsule, Long> {
}
