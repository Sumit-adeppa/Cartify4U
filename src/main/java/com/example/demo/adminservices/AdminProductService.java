package com.example.demo.adminservices;

import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductImageRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductImage;
import com.example.demo.sequence.SequenceGeneratorService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AdminProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CategoryRepository categoryRepository;
    private final SequenceGeneratorService sequenceGenerator;

    public AdminProductService(ProductRepository productRepository,
            ProductImageRepository productImageRepository,
            CategoryRepository categoryRepository,
            SequenceGeneratorService sequenceGenerator) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.categoryRepository = categoryRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    public Product addProductWithImage(String name,
            String description,
            Double price,
            Integer stock,
            Long categoryId,
            String imageUrl) {

        Category category = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        Product product = new Product();
        product.setProductId(sequenceGenerator.generateSequence(Product.SEQUENCE_NAME));
        product.setName(name);
        product.setDescription(description);
        product.setPrice(BigDecimal.valueOf(price));
        product.setStock(stock);
        product.setCategoryId(categoryId);
        product.setCategoryName(category.getCategoryName());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            ProductImage image = new ProductImage();
            image.setImageId(sequenceGenerator.generateSequence(ProductImage.SEQUENCE_NAME));
            image.setProductId(savedProduct.getProductId());
            image.setImageUrl(imageUrl);
            productImageRepository.save(image);
        }

        return savedProduct;
    }

    public void deleteProduct(Long productId) {

        if (!productRepository.existsByProductId(productId)) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        productImageRepository.deleteByProductId(productId);
        productRepository.deleteByProductId(productId);
    }

}
