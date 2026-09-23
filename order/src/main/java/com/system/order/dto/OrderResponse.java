package com.system.order.dto;

import com.system.order.entity.OrderEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {

    private Long id;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private String status;
    private String customerName;
    private LocalDateTime createdAt;

    public OrderResponse() {
    }

    public OrderResponse(OrderEntity entity) {
        this.id = entity.getId();
        this.productName = entity.getProductName();
        this.quantity = entity.getQuantity();
        this.price = entity.getPrice();
        this.status = entity.getStatus();
        this.customerName = entity.getCustomerName();
        this.createdAt = entity.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
