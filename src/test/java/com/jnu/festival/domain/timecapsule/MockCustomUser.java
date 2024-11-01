package com.jnu.festival.domain.timecapsule;

import com.jnu.festival.domain.user.entity.Role;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockSecurityContextFactory.class)
public @interface MockCustomUser {

    long id() default 1L;
    String nickname() default "test";
    String password() default "test";
    Role role() default Role.ROLE_USER;
}
