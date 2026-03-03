package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, ObjectId> {

    // Business ID based queries
    Optional<Product> findByProductId(Long productId);

    boolean existsByProductId(Long productId);

    void deleteByProductId(Long productId);

    List<Product> findByCategoryId(Long categoryId);
}
