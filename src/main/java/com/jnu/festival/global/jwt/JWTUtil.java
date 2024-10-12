package com.jnu.festival.global.jwt;

import com.jnu.festival.global.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt.access-token-expire-period}")
    private Long accessTokenExpirePeriod;

    private final SecretKey secretKey;

    public JWTUtil(@Value("${jwt.secret-key}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
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
}
