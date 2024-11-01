package com.jnu.festival.domain.timecapsule.repository;

import com.jnu.festival.domain.timecapsule.MockCustomUser;
import com.jnu.festival.domain.timecapsule.entity.Timecapsule;
import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.global.security.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TimecapsuleRepositoryTest {

    @Autowired
    private TimecapsuleRepository timecapsuleRepository;


    @Mock
    private User user;

    @Test
    @MockCustomUser
    void 타임캡슐_저장_테스트() {
        try {
            UserDetailsImpl principal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            User actualUser = principal.getUser();
            // given
            Timecapsule timecapsule = new Timecapsule(actualUser, "test", "test", true);

            //when
            Timecapsule result = timecapsuleRepository.save(timecapsule);

            //then
            assertThat(timecapsule).isSameAs(result);
            assertThat(timecapsule.getContent()).isEqualTo(result.getContent());
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
