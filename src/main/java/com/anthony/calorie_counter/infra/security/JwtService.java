package com.anthony.calorie_counter.infra.security;

import com.anthony.calorie_counter.exceptions.AuthenticationDataException;
import com.anthony.calorie_counter.exceptions.TokenCreateException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private final String ISSUER = "spring-security-jwt";
    private final JwtEncoder encoder;
    @Value("${jwt.expiration_time}")
    private Long expirationTime;

    public JwtService(JwtEncoder encoder) { this.encoder = encoder; }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        var claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationTime))
                .subject(authentication.getName())
                .claim("scope", scopes)
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
