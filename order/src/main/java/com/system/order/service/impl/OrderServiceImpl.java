package com.system.order.service.impl;

import com.system.order.dto.OrderRequest;
import com.system.order.dto.OrderResponse;
import com.system.order.entity.OrderEntity;
import com.system.order.repository.OrderRepository;
import com.system.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        OrderEntity entity = new OrderEntity(
                request.getProductName(),
                request.getQuantity(),
                request.getPrice(),
                request.getStatus(),
                request.getCustomerName()
        );
        return new OrderResponse(orderRepository.save(entity));
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(Long id, OrderRequest request) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        entity.setProductName(request.getProductName());
        entity.setQuantity(request.getQuantity());
        entity.setPrice(request.getPrice());
        entity.setStatus(request.getStatus());
        entity.setCustomerName(request.getCustomerName());

        return new OrderResponse(orderRepository.save(entity));
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        orderRepository.delete(entity);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return new OrderResponse(entity);
    }
}
