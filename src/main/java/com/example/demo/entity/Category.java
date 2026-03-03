package com.example.demo.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
public class Category {

    public static final String SEQUENCE_NAME = "categories_sequence";

    @Id
    private ObjectId id;

    private Long categoryId;   // Auto-generated
    private String categoryName;
    
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
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
	
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
    
}
