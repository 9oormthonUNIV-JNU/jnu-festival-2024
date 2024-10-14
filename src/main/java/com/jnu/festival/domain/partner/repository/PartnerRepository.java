package com.jnu.festival.domain.partner.repository;

import com.jnu.festival.domain.partner.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Optional<Partner> findById(Long id);
}
