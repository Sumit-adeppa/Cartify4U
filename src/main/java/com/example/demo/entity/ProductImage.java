package com.example.demo.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product_images")
public class ProductImage {

    public static final String SEQUENCE_NAME = "product_images_sequence";

    @Id
    private ObjectId id;

    private Long imageId;   // Auto-generated
    private String imageUrl;
    private Long productId;
    
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public Long getImageId() {
		return imageId;
	}
	
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}

}
