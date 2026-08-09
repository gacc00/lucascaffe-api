package com.paolo.lucascaffe.service;


import com.paolo.lucascaffe.dto.OrderRequest;
import com.paolo.lucascaffe.model.*;
import com.paolo.lucascaffe.repository.OrderRepository;
import com.paolo.lucascaffe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Order createOrder(OrderRequest request, User user){
        List<OrderItem> items = request.getItems().stream()
                .map(itemRequest -> {
                    Product product = productRepository.findById(itemRequest.getProductId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                    return OrderItem.builder()
                            .product(product)
                            .quantity(itemRequest.getQuantity())
                            .price(product.getPrice())
                            .build();
                })
                .toList();

        BigDecimal total = items.stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .user(user)
                .items(items)
                .total(total)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .notes(request.getNotes())
                .build();

        items.forEach(item -> item.setOrder(order));

        return orderRepository.save(order);
    }
    public List<Order> getUserOrders(User user){
        return orderRepository.findByUser(user);
    }
}
