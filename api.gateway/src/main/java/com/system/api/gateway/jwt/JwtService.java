package com.system.api.gateway.jwt;

import com.system.api.gateway.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final String DEFAULT_SECRET_KEY = "secret-key-for-api-gateway-microservice-1234567890";
    private static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60;

    private final String secretKey;

    public JwtService() {
        this(DEFAULT_SECRET_KEY);
    }

    public JwtService(String secretKey) {
        this.secretKey = secretKey;
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + JWT_TOKEN_VALIDITY);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + JWT_TOKEN_VALIDITY);
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            Claims claims = parseClaims(token);
            return username.equals(claims.getSubject()) && !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasAuthority(String token, String authority) {
        try {
            Claims claims = parseClaims(token);
            List<String> roles = claims.get("roles", List.class);
            if (roles == null || roles.isEmpty()) {
                return false;
            }
            return roles.stream().anyMatch(role -> role.equals(authority) || role.equals("ROLE_" + authority));
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
