package com.anthony.calorie_counter.infra.security;

import com.anthony.calorie_counter.exceptions.AuthenticationDataException;
import com.anthony.calorie_counter.exceptions.TokenCreateException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {
    private final String ISSUER = "spring-security-jwt";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration_time}")
    private Long expirationTime;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(now)
                    .withExpiresAt(now.plusSeconds(expirationTime))
                    .withSubject(authentication.getName())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new TokenCreateException("Error while generating token", e);
        }
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new AuthenticationDataException("Invalid token.");
        }
    }
}
