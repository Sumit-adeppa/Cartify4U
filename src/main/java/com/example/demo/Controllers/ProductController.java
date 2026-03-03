package com.example.demo.Controllers;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Services.ProductService;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(required = false) Long categoryId,
            HttpServletRequest request) {

        User authenticatedUser = (User) request.getAttribute("authenticatedUser");
        if (authenticatedUser == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Unauthorized access"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("user", Map.of(
                "name", authenticatedUser.getUsername(),
                "role", authenticatedUser.getRole().name()
        ));

        List<Product> products = productService.getProductsByCategory(categoryId);

        List<Map<String, Object>> productList = new ArrayList<>();

        for (Product product : products) {
            Map<String, Object> p = new HashMap<>();
            p.put("product_id", product.getProductId());
            p.put("name", product.getName());
            p.put("description", product.getDescription());
            p.put("price", product.getPrice());
            p.put("stock", product.getStock());

            List<String> images = productService.getProductImages(product.getProductId());
            p.put("images", images);

            productList.add(p);
        }

        response.put("products", productList);
        return ResponseEntity.ok(response);
    }
}
