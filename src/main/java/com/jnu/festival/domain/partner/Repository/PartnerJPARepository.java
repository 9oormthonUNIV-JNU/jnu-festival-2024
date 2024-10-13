package com.jnu.festival.domain.partner.Repository;

import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerJPARepository extends JpaRepository<Partner, Long> {
    Optional<Partner> findById(Long id);
}
