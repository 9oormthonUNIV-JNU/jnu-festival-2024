package com.jnu.festival.global.security.jwt;

import com.jnu.festival.domain.user.repository.UserRepository;
import com.jnu.festival.global.security.auth.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt.access-token-expire-period}")
    private Long accessTokenExpirePeriod;

    private final SecretKey secretKey;
    private final UserRepository userRepository;

    public JWTUtil(@Value("${jwt.secret-key}") String secret, UserRepository userRepository) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.userRepository = userRepository;
    }

    public Claims validateToken(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(UserDetailsImpl userDetails) {
        return Jwts.builder()
                .claim("nickname", userDetails.getUsername())
                .claim("role", userDetails.getRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirePeriod))
                .signWith(secretKey)
                .compact();
    }

    public String getToken(UserDetailsImpl userDetails) {
        return userRepository.findAccessTokenByNickname(userDetails.getUsername())
                .orElse(null);
    }
}
