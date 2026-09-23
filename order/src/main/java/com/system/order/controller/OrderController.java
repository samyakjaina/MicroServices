package com.system.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class OrderController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/orders-summary")
    public List<Map<String, Object>> getOrdersSummary() {
        return List.of(
                Map.of("id", 1, "product", "Laptop", "status", "CREATED"),
                Map.of("id", 2, "product", "Phone", "status", "SHIPPED")
        );
    }
}
