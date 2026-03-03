package com.example.demo.Controllers;

import com.example.demo.Services.CartService;
import com.example.demo.entity.User;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Get cart item count
    @GetMapping("/items/count")
    public ResponseEntity<Integer> getCartItemCount(@RequestParam String username, HttpServletRequest request) {

        User user = (User) request.getAttribute("authenticatedUser");
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        int count = cartService.getCartItemCount(user.getUserId());
        return ResponseEntity.ok(count);
    }

    // Get all cart items
    @GetMapping("/items")
    public ResponseEntity<Map<String, Object>> getCartItems(HttpServletRequest request) {

        User user = (User) request.getAttribute("authenticatedUser");
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(cartService.getCartItems(user.getUserId()));
    }

    // Add item to cart
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> request,
                                       HttpServletRequest httpRequest) {

        try {
            User user = (User) httpRequest.getAttribute("authenticatedUser");
            if (user == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "User not authenticated"));

            Long productId = Long.valueOf(request.get("productId").toString());
            int quantity = request.containsKey("quantity")
                    ? ((Number) request.get("quantity")).intValue() : 1;

            cartService.addToCart(user.getUserId(), productId, quantity);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Product added to cart successfully"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Update cart item quantity
    @PutMapping("/update")
    public ResponseEntity<?> updateCartItemQuantity(@RequestBody Map<String, Object> request,
                                                    HttpServletRequest httpRequest) {

        try {
            User user = (User) httpRequest.getAttribute("authenticatedUser");
            if (user == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "User not authenticated"));

            Long productId = Long.valueOf(request.get("productId").toString());
            int quantity = ((Number) request.get("quantity")).intValue();

            cartService.updateCartItemQuantity(user.getUserId(), productId, quantity);

            return ResponseEntity.ok(Map.of("message", "Cart updated successfully"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Delete cart item
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCartItem(@RequestBody Map<String, Object> request,
                                            HttpServletRequest httpRequest) {

        try {
            User user = (User) httpRequest.getAttribute("authenticatedUser");
            if (user == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "User not authenticated"));

            Long productId = Long.valueOf(request.get("productId").toString());
            cartService.deleteCartItem(user.getUserId(), productId);

            return ResponseEntity.ok(Map.of("message", "Item removed from cart"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
