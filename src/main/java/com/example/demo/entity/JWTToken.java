package com.example.demo.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "jwt_tokens")
public class JWTToken {

    public static final String SEQUENCE_NAME = "jwt_tokens_sequence";

    @Id
    private ObjectId id;

    private Long tokenId;   // Auto-generated
    private Long userId;
    private String token;
    private LocalDateTime expireAt;
    
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public Long getTokenId() {
		return tokenId;
	}
	
	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public LocalDateTime getExpireAt() {
		return expireAt;
	}
	
	public void setExpireAt(LocalDateTime expireAt) {
		this.expireAt = expireAt;
	}
	
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
}
