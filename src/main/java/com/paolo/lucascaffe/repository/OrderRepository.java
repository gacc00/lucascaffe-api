package com.paolo.lucascaffe.repository;

import com.paolo.lucascaffe.model.Order;
import com.paolo.lucascaffe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
