package com.example.demo.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart_items")
public class CartItem {

    public static final String SEQUENCE_NAME = "cart_items_sequence";

    @Id
    private ObjectId id;

    private Long cartId;   // Auto-generated

    private Long userId;
    private Long productId;
    private Integer quantity;
    
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public Long getCartId() {
		return cartId;
	}
	
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
    
}
