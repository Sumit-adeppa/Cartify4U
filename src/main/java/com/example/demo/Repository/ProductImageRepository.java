package com.example.demo.Repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProductImage;

@Repository
public interface ProductImageRepository extends MongoRepository<ProductImage, ObjectId> {

    List<ProductImage> findByProductId(Long productId);

    void deleteByProductId(Long productId);
}
