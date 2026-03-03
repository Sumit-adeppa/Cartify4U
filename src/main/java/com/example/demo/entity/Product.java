package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

    public static final String SEQUENCE_NAME = "product_sequence";

    @Id
    private ObjectId id;

    private Long productId;   // Auto-generated

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    private Long categoryId;
    private String categoryName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public Integer getStock() {
		return stock;
	}
	
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
}
