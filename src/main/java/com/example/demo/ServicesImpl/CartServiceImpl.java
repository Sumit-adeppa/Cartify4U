package com.example.demo.ServicesImpl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.ProductImageRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Services.CartService;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductImage;
import com.example.demo.sequence.SequenceGeneratorService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @Override
    public int getCartItemCount(Long userId) {
        List<CartItem> items = cartRepository.findByUserId(userId);
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    @Override
    public void addToCart(Long userId, Long productId, int quantity) {

        // Validate user by business ID
        userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Validate product by business ID
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Optional<CartItem> existingItem = cartRepository.findByUserIdAndProductId(userId, productId);

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartRepository.save(cartItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCartId(sequenceGenerator.generateSequence(CartItem.SEQUENCE_NAME));
            newItem.setUserId(userId);
            newItem.setProductId(productId);
            newItem.setQuantity(quantity);

            cartRepository.save(newItem);
        }
    }

    @Override
    public Map<String, Object> getCartItems(Long userId) {

        userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<CartItem> cartItems = cartRepository.findByUserId(userId);

        List<Map<String, Object>> products = new ArrayList<>();
        double overallTotalPrice = 0.0;

        for (CartItem cartItem : cartItems) {

            Product product = productRepository.findByProductId(cartItem.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            List<ProductImage> images = productImageRepository.findByProductId(product.getProductId());
            String imageUrl = images.isEmpty() ? null : images.get(0).getImageUrl();
            
            
            Map<String, Object> productDetails = new HashMap<>();
            
            productDetails.put("product_id", product.getProductId());
            productDetails.put("name", product.getName());
            productDetails.put("description", product.getDescription());
            productDetails.put("price_per_unit", product.getPrice());
            productDetails.put("quantity", cartItem.getQuantity());
            productDetails.put("imageUrl", imageUrl);

            double total = cartItem.getQuantity() * product.getPrice().doubleValue();
            productDetails.put("total_price", total);

            products.add(productDetails);
            overallTotalPrice += total;
        }

        Map<String, Object> cart = new HashMap<>();
        cart.put("products", products);
        cart.put("overall_total_price", overallTotalPrice);

        Map<String, Object> response = new HashMap<>();
        response.put("cart", cart);

        return response;
    }

    @Override
    public void updateCartItemQuantity(Long userId, Long productId, int quantity) {

        CartItem cartItem = cartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));

        if (quantity <= 0) {
            cartRepository.deleteByUserIdAndProductId(userId, productId);
        } else {
            cartItem.setQuantity(quantity);
            cartRepository.save(cartItem);
        }
    }

    @Override
    public void deleteCartItem(Long userId, Long productId) {
        cartRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
