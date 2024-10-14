package com.jnu.festival.domain.bookmark.entity;

import com.jnu.festival.domain.content.entity.Content;
import com.jnu.festival.domain.partner.entity.Partner;
import com.jnu.festival.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "partner_bookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update partner_bookmark set is_deleted = true where id = ?")
@Getter
public class PartnerBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    @Column(name="is_deleted",nullable = false)
    private boolean isDeleted;

    @Builder
    public PartnerBookmark(User user, Partner partner, boolean isDeleted) {
        this.user = user;
        this.partner = partner;
        this.isDeleted = isDeleted;
    }

    public void updateIsDeleted(){
        this.isDeleted = Boolean.FALSE;
    }
}
