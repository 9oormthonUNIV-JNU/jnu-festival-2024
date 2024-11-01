package com.jnu.festival.domain.timecapsule;

import com.jnu.festival.domain.user.entity.User;
import com.jnu.festival.global.security.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class MockSecurityContextFactory implements WithSecurityContextFactory<MockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(MockCustomUser customUser) {
        User mockUser = User.builder()
                .nickname(customUser.nickname())
                .password(customUser.password())
                .role(customUser.role())
                .build();

        UserDetailsImpl principal = new UserDetailsImpl(mockUser);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                principal, "", principal.getAuthorities()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(token);
        return context;
    }
}
