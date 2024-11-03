package com.jnu.festival.global.security.jwt;


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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        List<String> passUris = Arrays.asList(
//                "/api/v1/login"
//        );
//
//        // 현재 회원가입과 로그인이 동일
//        // JWTAuthenticationFilter가 DefaultAuthenticationFilter보다 앞에 위치
//        if (passUris.contains(request.getRequestURI())) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        String rawToken = request.getHeader("Authorization");

        // 의문사항
        // 1. 아래와 같이 작성한 이유가 분명 있었는데 기억이 안 난다...
//        if (unpreparedToken == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }

//        if (rawToken == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        if (rawToken == null || !rawToken.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCode.INVALID_HEADER_ERROR);
        }

        String token = rawToken.substring("Bearer ".length());

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
