package org.example.studentsystem.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "student-system-secret-key-1234567890";

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // 生成token
    public static String generateToken(String userId, String userName) {

        return Jwts.builder()
                .subject(userId)
                .claim("userName", userName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(KEY)
                .compact();
    }

    // 解析token
    public static Claims getClaims(String token) {

        return Jwts.parser()

                .verifyWith(KEY)

                .build()

                .parseSignedClaims(token)

                .getPayload();

    }

    public static String getUserId(String token) {

        return getClaims(token).getSubject();

    }

    public static String getUserName(String token) {

        return getClaims(token).get("userName", String.class);

    }
}