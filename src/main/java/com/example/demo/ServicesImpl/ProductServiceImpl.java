package com.example.demo.ServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductImageRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Services.ProductService;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductImage;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {

        if (categoryId == null) {
            return productRepository.findAll();
        }

        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<String> getProductImages(Long productId) {

        if (productId == null) {
            return new ArrayList<>();
        }

        List<ProductImage> productImages =
                productImageRepository.findByProductId(productId);

        List<String> imageUrls = new ArrayList<>();

        for (ProductImage image : productImages) {
            if (image.getImageUrl() != null) {
                imageUrls.add(image.getImageUrl());
            }
        }

        return imageUrls;
    }
}
