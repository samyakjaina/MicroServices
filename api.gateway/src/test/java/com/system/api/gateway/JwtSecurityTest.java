package com.system.api.gateway;

import com.system.api.gateway.jwt.JwtService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtSecurityTest {

    @Test
    void shouldCreateAndValidateJwtToken() {
        JwtService jwtService = new JwtService("test-secret-key-for-api-gateway-1234567890");

        String token = jwtService.generateToken("admin");

        assertNotNull(token);
        assertEquals("admin", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, "admin"));
    }
}
