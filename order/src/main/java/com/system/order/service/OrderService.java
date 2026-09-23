package com.system.order.service;

import com.system.order.dto.OrderRequest;
import com.system.order.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse updateOrder(Long id, OrderRequest request);
    void deleteOrder(Long id);
    List<OrderResponse> getAllOrders();
    OrderResponse getOrderById(Long id);
}
