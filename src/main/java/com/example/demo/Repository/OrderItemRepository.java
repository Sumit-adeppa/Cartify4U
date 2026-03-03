package com.example.demo.Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.OrderItem;

@Repository
public interface OrderItemRepository extends MongoRepository<OrderItem, ObjectId> {

    List<OrderItem> findByOrderId(Long orderId);
}

