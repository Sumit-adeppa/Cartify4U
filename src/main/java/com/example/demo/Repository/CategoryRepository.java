package com.example.demo.Repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, ObjectId> {

    Optional<Category> findByCategoryId(Long categoryId);

    Optional<Category> findByCategoryNameIgnoreCase(String categoryName);

    boolean existsByCategoryNameIgnoreCase(String categoryName);
}
