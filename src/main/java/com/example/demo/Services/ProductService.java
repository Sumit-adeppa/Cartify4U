package com.example.demo.Services;

import java.util.List;

import com.example.demo.entity.Product;

public interface ProductService {

    List<Product> getProductsByCategory(Long categoryId);

    List<String> getProductImages(Long productId);

}
