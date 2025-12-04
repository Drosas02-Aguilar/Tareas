package com.activities.group.jwt;

import com.activities.group.Entity.Result;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    Result result = new Result();

    private final String tokens = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.\n"
            + "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkRhbWlhbiIsImlhdCI6MTUxNjIzOTAyMn0.\n"
            + "SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    private final long expirationms = 86400000;

    public String generatedToke(String username) {

        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationms);

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(Keys.hmacShaKeyFor(tokens.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extracUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(tokens.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isValid(String token) {
        try {

            extracUsername(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
