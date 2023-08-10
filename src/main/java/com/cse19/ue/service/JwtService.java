package com.cse19.ue.service;

import com.cse19.ue.dto.Decoded;
import com.cse19.ue.dto.Token;
import com.cse19.ue.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import java.security.Key;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long JWT_EXPIRATION; // in milliseconds


    public Token generateJwtToken(Map<String, Object> extraClaims, String subject) {
        String accessToken = generateAccessToken(extraClaims, subject);
        int expiresIn = (int) (JWT_EXPIRATION / 1000);


        return Token.builder()
                .token(accessToken)
                .expiresIn(expiresIn)
                .tokenType("Bearer")
                .build();
    }


    private String generateAccessToken(Map<String, Object> extraClaims, String subject) {
        Date issuerTime = new Date();
        Date expirationTime = new Date(issuerTime.getTime() + JWT_EXPIRATION);


        return Jwts.builder()
                .setIssuer("university-entry-service")
                .setAudience("university-entry")
                .setClaims(extraClaims)
                .setSubject(subject)  // set subject something unique
                .setIssuedAt(issuerTime)
                .setExpiration(expirationTime)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public Decoded decodeJwtToken(String token) {
        Claims claims = extractAllClaims(token);

        if (claims.getExpiration() == null || claims.getIssuedAt() == null  ||
                claims.getSubject() == null) {
            throw new MalformedJwtException("Invalid token.");
        }

//        in here role should be ENUM Role class how to use that in here
        return new Decoded(
                claims.getExpiration(),
                claims.getIssuedAt(),
                claims.getSubject(),
                Role.valueOf(claims.get("role", String.class))
        );
    }

//    private boolean isTokenExpired(String token) throws InvalidJwtTokenException {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) throws InvalidJwtTokenException {
//        return extractClaim(token, Claims::getExpiration);
//    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

//    public String extractEmail(String token)  {
//        return extractClaim(token, Claims::getSubject);
//    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
