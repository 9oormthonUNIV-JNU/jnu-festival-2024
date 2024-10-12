package com.jnu.festival.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.festival.global.error.ErrorCode;
import com.jnu.festival.global.error.ExceptionDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        Map<String, Object> responsebody = new HashMap<>();
        responsebody.put("success", false);
        responsebody.put("data", null);
        responsebody.put("error", ExceptionDto.of(ErrorCode.ACCESS_DENIED));

        response.getWriter().write(new ObjectMapper().writeValueAsString(responsebody));
    }
}
