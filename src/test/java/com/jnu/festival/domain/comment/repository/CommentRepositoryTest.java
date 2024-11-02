package com.jnu.festival.domain.comment.repository;

import com.jnu.festival.domain.booth.entity.Booth;
import com.jnu.festival.domain.booth.entity.BoothCategory;
import com.jnu.festival.domain.booth.entity.Period;
import com.jnu.festival.domain.comment.entity.Comment;
import com.jnu.festival.domain.common.Location;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.global.security.UserDetailsImpl;
import com.jnu.festival.global.utils.MockCustomUser;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PlatformTransactionManager transactionManager;

    private Booth booth;
    private User actualUser;

    TransactionStatus status;

    @BeforeEach
    void beforeEach() {
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        actualUser = principal.getUser();
        booth = Booth.builder()
                .name("Sample Booth")
                .location(Location.STADIUM) // 예시 Enum 값
                .index(1)
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 1, 2))
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(18, 0))
                .description("This is a sample booth.")
                .category(BoothCategory.FOOD) // 예시 Enum 값
                .period(Period.ALLTIME) // 예시 Enum 값
                .build();
        entityManager.persist(actualUser);
        entityManager.persist(booth);
    }

    @AfterEach
    void afterEach() {
        transactionManager.rollback(status);
    }

    @Test
    @MockCustomUser
    void createComment() {

        Comment comment1 = Comment.builder()
                .user(actualUser)
                .booth(booth)
                .content("Comment 1")
                .build();

        Comment savedComment = commentRepository.save(comment1);

        // 검증
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getId()).isNotNull();
        assertThat(savedComment.getUser()).isEqualTo(actualUser);
        assertThat(savedComment.getBooth()).isEqualTo(booth);
    }

    @Test
    @MockCustomUser
    void findAllByBooth_테스트() {
        Comment comment1 = Comment.builder()
                .user(actualUser)
                .booth(booth)
                .content("Comment 1")
                .build();

        Comment comment2 = Comment.builder()
                .user(actualUser)
                .booth(booth)
                .content("Comment 2")
                .build();

        commentRepository.save(comment1);
        commentRepository.save(comment2);

        List<Comment> comments = commentRepository.findAllByBooth(booth);

        assertThat(comments).hasSize(2);
        assertThat(comments).containsExactlyInAnyOrder(comment1, comment2);
    }

    @Test
    @MockCustomUser
    void delete() {
        Comment comment1 = Comment.builder()
                .user(actualUser)
                .booth(booth)
                .content("Comment 1")
                .build();

        commentRepository.delete(comment1);

        // Then: 삭제 확인
        Optional<Comment> deletedComment = commentRepository.findById(1L);
        assertThat(deletedComment).isEmpty();
    }

}
