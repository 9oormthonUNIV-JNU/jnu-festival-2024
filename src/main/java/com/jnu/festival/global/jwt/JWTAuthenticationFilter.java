package com.jnu.festival.global.jwt;


import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        List<String> passUris = Arrays.asList(
                "/api/v1/login"
        );

        if (passUris.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String unpreparedToken = request.getHeader("Authorization");

        // 의문사항
        // 1. 아래와 같이 작성한 이유가 분명 있었는데 기억이 안 난다...
//        if (unpreparedToken == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        System.out.println(unpreparedToken);

        if (unpreparedToken == null || !unpreparedToken.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCode.INVALID_HEADER_ERROR);
        }

        String token = unpreparedToken.substring("Bearer ".length());

        Claims claims = jwtUtil.validateToken(token);

        String nickname = claims.get("nickname", String.class);
        UserDetails userDetails = userDetailsService.loadUserByUsername(nickname);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        filterChain.doFilter(request, response);
    }
}
