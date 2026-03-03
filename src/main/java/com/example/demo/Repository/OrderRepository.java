package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;

@Repository
public interface OrderRepository extends MongoRepository<Order, ObjectId> {

    Optional<Order> findByOrderId(Long orderId);

    Optional<Order> findByRazorpayOrderId(String razorpayOrderId);

    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByUserIdAndStatus(Long userId, OrderStatus status);
}

