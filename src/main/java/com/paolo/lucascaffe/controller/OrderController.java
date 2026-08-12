package com.paolo.lucascaffe.controller;

import com.paolo.lucascaffe.dto.OrderRequest;
import com.paolo.lucascaffe.model.Order;
import com.paolo.lucascaffe.model.User;
import com.paolo.lucascaffe.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name= "Ordenes", description = "Creación y Obtención de ordenes")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Postear una orden", description = "Crea una Orden")
    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody OrderRequest request,
            @AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(request,user));
    }
    @Operation(summary = "Recibir la orden", description = "haz el llamado para obtener la orden")
    @GetMapping
    public ResponseEntity<List<Order>> getUserOrders(
            @AuthenticationPrincipal User user){
        return ResponseEntity.ok(orderService.getUserOrders(user));
    }
}
