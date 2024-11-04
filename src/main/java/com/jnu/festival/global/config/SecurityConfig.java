package com.jnu.festival.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnu.festival.domain.user.service.UserService;
import com.jnu.festival.global.security.auth.DefaultAccessDeniedHandler;
import com.jnu.festival.global.security.auth.DefaultAuthenticationEntryPoint;
import com.jnu.festival.global.security.auth.DefaultAuthenticationFilter;
import com.jnu.festival.global.security.auth.DefaultLogoutSuccessHandler;
import com.jnu.festival.global.security.jwt.JWTAuthenticationFilter;
import com.jnu.festival.global.security.jwt.JWTExceptionFilter;
import com.jnu.festival.global.security.jwt.JWTUtil;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final Validator validator;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;
    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;
    private final DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;
    private final CorsConfig corsConfig;

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
                .headers(headerConfig -> headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/login",
                                "/api/v1/users/check-admins",
                                "/api/v1/zones/**",
                                "/api/v1/partners/**",
                                "/api/v1/contents/**",
                                "/api/v1/booths", "/api/v1/booths/{boothId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/timecapsules").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/booths/{boothId}/comments").permitAll()
                        .requestMatchers("/api/v1/admins/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/logout", "POST"))
                        .logoutSuccessHandler(defaultLogoutSuccessHandler))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new DefaultAuthenticationFilter(authenticationManager(), objectMapper, validator, userService, jwtUtil), LogoutFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(jwtUtil, userDetailsService), DefaultAuthenticationFilter.class)
                .addFilterBefore(new JWTExceptionFilter(), JWTAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                        .accessDeniedHandler(defaultAccessDeniedHandler));
        return http.build();
    }
}
