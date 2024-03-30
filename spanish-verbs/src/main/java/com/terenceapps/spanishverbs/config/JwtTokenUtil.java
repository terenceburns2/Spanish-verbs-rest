package com.terenceapps.spanishverbs.config;

import com.terenceapps.spanishverbs.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String notSoSecretString;
    private final Logger logger = Logger.getLogger(JwtTokenUtil.class.getName());

    public String getUser(String token) {
        try {
            Jws<Claims> jws = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            return jws.getPayload().getSubject();
        } catch (JwtException e) {
            logger.info("Token failed to pass validation: " + e);
        }
        return null;
    }

    public boolean validate(String token) {
        try {
            Jws<Claims> jws = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            if (jws.getPayload().getExpiration().getTime() < new Date().getTime()) {
                return false;
            }
        } catch (JwtException e) {
            logger.info("Token failed to pass validation: " + e);
            return false;
        }
        return true;
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
