package com.terenceapps.spanishverbs.config;

import com.terenceapps.spanishverbs.exception.InvalidAccessTokenException;
import com.terenceapps.spanishverbs.model.User;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String notSoSecretString;

    public String getUser(String token) {
        try {
            Jws<Claims> jws = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            return jws.getPayload().getSubject();
        } catch (JwtException e) {
            throw new InvalidAccessTokenException("Token validation failed.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidAccessTokenException("Token cannot be parsed and validated.", e);
        }
    }

    public boolean validate(String token) {
        try {
            Jws<Claims> jws = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            return jws.getPayload().getExpiration().getTime() >= new Date().getTime();
        } catch (JwtException e) {
            throw new InvalidAccessTokenException("Token validation failed.", e);
        } catch (IllegalArgumentException e) {
            throw new InvalidAccessTokenException("Token cannot be parsed and validated.", e);
        }
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .issuer("spanishverbs")
                .subject(user.getUsername())
                .claim("user_id", user.getId())
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(getKey())
                .compact();

    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(notSoSecretString));
    }
}
