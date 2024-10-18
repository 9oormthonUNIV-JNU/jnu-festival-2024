package com.jnu.festival.domain.like.entity;


import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update likes set is_deleted = true where id = ?")
@Getter
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booth_id", nullable = false)
    private Booth booth;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Builder
    public Like(Long id, User user, Booth booth, Boolean isDeleted) {
        this.id = id;
        this.user = user;
        this.booth = booth;
        this.isDeleted = isDeleted;
    }

    public void updateIsDeleted() {
        this.isDeleted = Boolean.FALSE;
    }
}
