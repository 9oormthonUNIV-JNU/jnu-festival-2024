package com.jnu.festival.global.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.festival.domain.user.dto.request.UserRegisterDto;
import com.jnu.festival.domain.user.service.UserService;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.exception.BusinessException;
import com.jnu.festival.global.common.ResponseDto;
import com.jnu.festival.global.security.jwt.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_REQUEST_MATCHER = new AntPathRequestMatcher("/api/v1/login", "POST");
//    private static final String CONTENT_TYPE = "application/json";
//    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final Validator validator;
    private final UserService userService;
    private final JWTUtil jwtUtil;

    public DefaultAuthenticationFilter(AuthenticationManager authenticationManager,
                                       ObjectMapper objectMapper,
                                       Validator validator,
                                       UserService userService,
                                       JWTUtil jwtUtil) {
        super(DEFAULT_ANT_REQUEST_MATCHER);
        super.setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
        this.validator = validator;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        if (!CONTENT_TYPE.equals(request.getContentType())) {
//            throw new BusinessException(ErrorCode.INVALID_HEADER_ERROR);
//        }

        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        UserRegisterDto userRegisterDto = objectMapper.readValue(body, UserRegisterDto.class);

        Set<ConstraintViolation<UserRegisterDto>> violations = validator.validate(userRegisterDto);
        if (!violations.isEmpty()) {
            StringBuilder errorFields = new StringBuilder();
            for (ConstraintViolation<UserRegisterDto> violation : violations) {
                errorFields.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append(" ");
            }

            ConstraintViolationException e = new ConstraintViolationException(violations);
            throw new AuthenticationServiceException(errorFields.toString(), e);
        }

        userService.signup(userRegisterDto);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRegisterDto.nickname(), userRegisterDto.password());
        return this.getAuthenticationManager().authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        String accessToken = jwtUtil.getToken(userDetails);
        if (accessToken == null) {
            accessToken = jwtUtil.generateToken(userDetails);
            userService.updateAccessToken(userDetails, accessToken);
        }
        response.setHeader("Access-Token", accessToken);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());

        response.getWriter().write(new ObjectMapper().writeValueAsString(ResponseDto.ok(null)));

//        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        if (failed.getCause() instanceof ConstraintViolationException) {
            String[] errors = failed.getMessage().split("(?<=\\.) ");

            Map<String, String> errorFields = new HashMap<>();
            for (String error: errors) {
                String[] errorMessage = error.split(": ", 2);
                errorFields.put(errorMessage[0], errorMessage[1]);
            }

            Map<String, Object> errorMessages = new HashMap<>();
            errorMessages.put("message", "유효하지 않은 인자입니다.");
            errorMessages.put("error_fields", errorFields);


            Map<String, Object> responsebody = new HashMap<>();
            responsebody.put("success", false);
            responsebody.put("data", null);
            responsebody.put("error", errorMessages);

            response.getWriter().write(new ObjectMapper().writeValueAsString(responsebody));
        } else {
            response.getWriter().write(new ObjectMapper().writeValueAsString(ResponseDto.fail(new BusinessException(ErrorCode.DUPLICATED_USER))));
        }
    }
}
