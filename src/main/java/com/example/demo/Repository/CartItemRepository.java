package com.example.demo.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CartItem;

@Repository
public interface CartItemRepository extends MongoRepository<CartItem, ObjectId> {

    long countByUserId(Long userId);
}
