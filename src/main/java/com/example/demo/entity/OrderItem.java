package com.example.demo.entity;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order_items")
public class OrderItem {

    public static final String SEQUENCE_NAME = "order_item_sequence";

    @Id
    private ObjectId id;

    private Long orderItemId;   // Auto-generated
    private Long orderId;
    private Long productId;

    private Integer quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal totalPrice;
    
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public Long getOrderItemId() {
		return orderItemId;
	}
	
	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	
	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}
	
	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
    
}
