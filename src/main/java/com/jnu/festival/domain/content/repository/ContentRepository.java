package com.jnu.festival.domain.content.repository;

import com.jnu.festival.domain.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
