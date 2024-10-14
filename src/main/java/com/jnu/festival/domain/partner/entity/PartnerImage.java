package com.jnu.festival.domain.partner.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partner_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PartnerImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    @Column(name = "url", nullable = false)
    private String url;

    @Builder
    public PartnerImage(Partner partner, String url) {
        this.partner = partner;
        this.url = url;
    }
}
