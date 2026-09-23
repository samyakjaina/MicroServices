package com.system.order;

import com.system.order.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = "jwt.secret=secret-key-for-api-gateway-microservice-1234567890")
class JwtValidationTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void shouldCreateAndValidateJwtToken() {
        JwtService localJwtService = new JwtService("test-secret-key-for-order-service-1234567890");

        String token = localJwtService.generateToken("admin");

        assertNotNull(token);
        assertEquals("admin", localJwtService.extractUsername(token));
        assertTrue(localJwtService.isTokenValid(token, "admin"));
    }

    @Test
    void shouldUseConfiguredSecretForTokenValidation() {
        JwtService gatewayJwtService = new JwtService("secret-key-for-api-gateway-microservice-1234567890");
        String token = gatewayJwtService.generateToken("admin");

        assertEquals("admin", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, "admin"));
    }
}
