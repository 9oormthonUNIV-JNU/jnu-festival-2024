package com.jnu.festival.domain.timeCapsule.repository;

import com.jnu.festival.domain.timeCapsule.entity.TimeCapsule;
import com.jnu.festival.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeCapsuleRepository extends JpaRepository<TimeCapsule, Long> {
    List<TimeCapsule> findByUser(User user);
    List<TimeCapsule> findByIsPublicTrueAndIdIsNot(User user);
}
