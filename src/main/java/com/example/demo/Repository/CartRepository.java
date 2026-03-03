package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CartItem;

@Repository
public interface CartRepository extends MongoRepository<CartItem, ObjectId> {

    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);

    List<CartItem> findByUserId(Long userId);

    void deleteByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserId(Long userId);
}
