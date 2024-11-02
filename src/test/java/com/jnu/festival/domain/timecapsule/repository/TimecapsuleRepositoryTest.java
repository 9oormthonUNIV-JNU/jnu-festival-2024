package com.jnu.festival.domain.timecapsule.repository;

import com.jnu.festival.domain.timecapsule.MockCustomUser;
import com.jnu.festival.domain.timecapsule.entity.Timecapsule;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.security.UserDetailsImpl;
import java.util.List;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@EnableJpaAuditing
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TimecapsuleRepositoryTest {

    @Autowired
    private TimecapsuleRepository timecapsuleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus status;

    @BeforeEach
    void beforeEach() {
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void afterEach() {
        transactionManager.rollback(status);
    }

    @Test
    @MockCustomUser
    void 타임캡슐_저장_테스트() {
        try {
            // given
            UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            User actualUser = principal.getUser();
            // 먼저 ActualUser가 데이터베이스에 존재하는 실제 User인지 체크가 선행되어야함. -> UserRepository를 사용하고 싶진 않음.
            User savedUser = entityManager.persist(actualUser);
            Timecapsule timecapsule = new Timecapsule(savedUser, "test", "test", true);

            //when
            Timecapsule result = timecapsuleRepository.save(timecapsule);

            //then
            assertThat(timecapsule).isSameAs(result);
            assertThat(timecapsule.getContent()).isEqualTo(result.getContent());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Test
    @MockCustomUser
    void 이메일_NULL_예외_테스트() {
        try {
            //given
            UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            User actualUser = principal.getUser();

            // 먼저 ActualUser가 데이터베이스에 존재하는 실제 User인지 체크가 선행되어야함. -> UserRepository를 사용하고 싶진 않음.
            User savedUser = entityManager.persist(actualUser);
            Timecapsule timecapsule = new Timecapsule(savedUser, null, "test", true);

            //when
            //then
            assertThatThrownBy(() -> timecapsuleRepository.save(timecapsule))
                    .isInstanceOf(DataIntegrityViolationException.class);
        } catch (Exception e) {

        }
    }

    @Test
    @MockCustomUser
    void CONTENT_NULL_예외_테스트() {
        //given
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User actualUser = principal.getUser();

        // 먼저 ActualUser가 데이터베이스에 존재하는 실제 User인지 체크가 선행되어야함. -> UserRepository를 사용하고 싶진 않음.
        User savedUser = entityManager.persist(actualUser);
        Timecapsule timecapsule = new Timecapsule(savedUser, "test", null, true);


        //when
        //then
        assertThatThrownBy(() -> timecapsuleRepository.save(timecapsule))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @MockCustomUser
    void findById_테스트() {
        //given
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User actualUser = principal.getUser();

        // 먼저 ActualUser가 데이터베이스에 존재하는 실제 User인지 체크가 선행되어야함. -> UserRepository를 사용하고 싶진 않음.
        User savedUser = entityManager.persist(actualUser);

        Timecapsule timecapsule = new Timecapsule(savedUser, "test", "test", true);

        Timecapsule savedTimecapsule = timecapsuleRepository.save(timecapsule);
        Long timecapsuleId = savedTimecapsule.getId();

        //when
        Optional<Timecapsule> foundTimecapsule = timecapsuleRepository.findById(timecapsuleId);

        //then
        assertThat(foundTimecapsule).isPresent();
        assertThat(foundTimecapsule.get().getId()).isEqualTo(timecapsuleId);
        assertThat(foundTimecapsule.get().getMailAddress()).isEqualTo("test");
        assertThat(foundTimecapsule.get().getContent()).isEqualTo("test");
        assertThat(foundTimecapsule.get().getIsPublic()).isEqualTo(true);
    }

    @Test
    @MockCustomUser
    void findBy_empty_테스트() {
        //given
        Long timecapsuleId = 10000000000L;

        //when
        //then
        assertThatThrownBy(() -> timecapsuleRepository.findById(timecapsuleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_TIMECAPSULE)))
                .isInstanceOf(BusinessException.class)
                .hasMessage("존재하지 않는 타임캡슐입니다.");
    }

    @Test
    @MockCustomUser
    void findAllByUser_테스트() {
        //given
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User actualUser = principal.getUser();
        User savedUser = entityManager.persist(actualUser);

        Timecapsule timecapsule1 = new Timecapsule(savedUser, "test1", "content1", true);
        Timecapsule timecapsule2 = new Timecapsule(savedUser, "test2", "content2", true);
        timecapsuleRepository.save(timecapsule1);
        timecapsuleRepository.save(timecapsule2);

        //when
        List<Timecapsule> timecapsules = timecapsuleRepository.findAllByUser(savedUser);

        // then
        assertThat(timecapsules).isNotEmpty();
        assertThat(timecapsules).hasSize(2); // 저장한 타임캡슐 수와 일치하는지 확인
        assertThat(timecapsules).extracting("mailAddress", String.class).contains("test1", "test2");
        assertThat(timecapsules).extracting("content").containsExactlyInAnyOrder("content1", "content2");
    }

    @Test
    @MockCustomUser
    void findAllByIsPublicTrue_테스트() {
        // given
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User actualUser = principal.getUser();
        User savedUser = entityManager.persist(actualUser);

        // 공개 타임캡슐 생성
        Timecapsule publicTimecapsule1 = new Timecapsule(savedUser, "public1", "content1", true);
        Timecapsule publicTimecapsule2 = new Timecapsule(savedUser, "public2", "content2", true);

        // 비공개 타임캡슐 생성
        Timecapsule privateTimecapsule = new Timecapsule(savedUser, "private", "privateContent", false);

        // 타임캡슐 저장
        timecapsuleRepository.save(publicTimecapsule1);
        timecapsuleRepository.save(publicTimecapsule2);
        timecapsuleRepository.save(privateTimecapsule);

        // when
        List<Timecapsule> publicTimecapsules = timecapsuleRepository.findAllByIsPublicTrue();

        // then
        assertThat(publicTimecapsules).isNotEmpty();
        assertThat(publicTimecapsules).hasSize(2); // 공개 타임캡슐 수와 일치하는지 확인
        assertThat(publicTimecapsules).extracting("mailAddress").containsExactlyInAnyOrder("public1", "public2");
    }

    @Test
    @MockCustomUser
    void deleteTimecapsule_테스트() {
        // given
        UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User actualUser = principal.getUser();
        User savedUser = entityManager.persist(actualUser);

        // 타임캡슐 생성 및 저장
        Timecapsule timecapsule = new Timecapsule(savedUser, "test@gmail.com", "testContent", true);
        Timecapsule savedTimecapsule = timecapsuleRepository.save(timecapsule);
        Long timecapsuleId = savedTimecapsule.getId();

        // when
        timecapsuleRepository.delete(savedTimecapsule);

        // then
        Optional<Timecapsule> foundTimecapsule = timecapsuleRepository.findById(timecapsuleId);
        assertThat(foundTimecapsule).isNotPresent(); // 삭제된 타임캡슐이 존재하지 않는지 확인
    }
}
