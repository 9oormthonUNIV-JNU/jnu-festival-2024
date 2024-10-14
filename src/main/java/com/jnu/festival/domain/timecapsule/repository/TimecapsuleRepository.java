package com.jnu.festival.domain.timecapsule.repository;

import com.jnu.festival.domain.timecapsule.entity.Timecapsule;
import com.jnu.festival.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimecapsuleRepository extends JpaRepository<Timecapsule, Long> {
    List<Timecapsule> findAllByUser(User user);

    List<Timecapsule> findAllByIsPublicTrue();
}
