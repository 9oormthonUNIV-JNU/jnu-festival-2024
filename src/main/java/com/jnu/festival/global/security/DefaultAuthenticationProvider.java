package com.jnu.festival.global.security;

import com.jnu.festival.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class DefaultAuthenticationProvider extends DaoAuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String nickname = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(nickname);
        } catch (UsernameNotFoundException e) {
            userService.join(nickname, password);
            userDetails = userDetailsService.loadUserByUsername(nickname);
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
