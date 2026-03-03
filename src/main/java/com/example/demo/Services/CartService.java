package com.example.demo.Services;

import java.util.Map;

public interface CartService {

    int getCartItemCount(Long userId);

    void addToCart(Long userId, Long productId, int quantity);

    Map<String, Object> getCartItems(Long userId);

    void updateCartItemQuantity(Long userId, Long productId, int quantity);

    void deleteCartItem(Long userId, Long productId);

}
