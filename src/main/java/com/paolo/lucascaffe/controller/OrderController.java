package com.paolo.lucascaffe.controller;

import com.paolo.lucascaffe.dto.OrderRequest;
import com.paolo.lucascaffe.model.Order;
import com.paolo.lucascaffe.model.User;
import com.paolo.lucascaffe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody OrderRequest request,
            @AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(request,user));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getUserOrders(
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(orderService.getUserOrders(user));
    }
}
