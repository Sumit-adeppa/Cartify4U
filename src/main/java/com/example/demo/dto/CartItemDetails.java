package com.example.demo.dto;

import org.bson.types.ObjectId;

import java.math.BigDecimal;

// Class to hold detailed cart item information
public class CartItemDetails {
    private ObjectId productId;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal pricePerUnit;
    private int quantity;
    private BigDecimal totalPrice;

    public CartItemDetails(ObjectId productId, String name, String description, String imageUrl, BigDecimal pricePerUnit, int quantity, BigDecimal totalPrice) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Getters and setters

    public ObjectId getProductId() {
        return productId;
    }

    public ObjectId setProductId(ObjectId productId) {
        return this.productId = productId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}