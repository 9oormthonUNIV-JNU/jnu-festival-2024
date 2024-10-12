package com.jnu.festival.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.festival.domain.user.dto.request.UserRegisterDto;
import com.jnu.festival.global.util.ResponseDto;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.jwt.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DefaultAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_REQUEST_MATCHER = new AntPathRequestMatcher("/api/v1/login", "POST");
    private static final String CONTENT_TYPE = "application/json";
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final JWTUtil jwtUtil;

    public DefaultAuthenticationFilter(AuthenticationManager authenticationManager,
                                       ObjectMapper objectMapper,
                                       JWTUtil jwtUtil) {
        super(DEFAULT_ANT_REQUEST_MATCHER);
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!CONTENT_TYPE.equals(request.getContentType())) {
            throw new BusinessException(ErrorCode.INVALID_HEADER_ERROR);
        }

        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        UserRegisterDto userRegisterDto = objectMapper.readValue(body, UserRegisterDto.class);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRegisterDto.getNickname(), userRegisterDto.getPassword());
        return this.authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        String accessToken = jwtUtil.generateToken(userDetails);
        response.setHeader("access-token", accessToken);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());

        response.getWriter().write(new ObjectMapper().writeValueAsString(ResponseDto.ok(null)));

//        super.successfulAuthentication(request, response, chain, authResult);
    }


}
