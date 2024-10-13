package com.jnu.festival.domain.bookmark.entity;

import com.jnu.festival.domain.content.entity.Content;
import com.jnu.festival.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "content_bookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update content_bookmark set is_deleted = true where id = ?")
@Getter
public class ContentBookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Builder
    public ContentBookmark(User user, Content content, Boolean isDeleted) {
        this.user = user;
        this.content = content;
        this.isDeleted = isDeleted;
    }

    public void updateIsDeleted() {
        this.isDeleted = Boolean.FALSE;
    }
}
