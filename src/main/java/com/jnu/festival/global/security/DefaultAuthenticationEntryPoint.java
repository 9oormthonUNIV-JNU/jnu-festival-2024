package com.jnu.festival.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.ExceptionDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = request.getAttribute("exception") == null?
                ErrorCode.NOT_FOUND_END_POINT : (ErrorCode) request.getAttribute("exception");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());

        Map<String, Object> responsebody = new HashMap<>();
        responsebody.put("success", false);
        responsebody.put("data", null);
        responsebody.put("error", ExceptionDto.of(errorCode));

        response.getWriter().write(new ObjectMapper().writeValueAsString(responsebody));
    }
}
