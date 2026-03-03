package com.example.demo.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.JWTToken;

@Repository
public interface JWTTokenRepository extends MongoRepository<JWTToken, ObjectId> {

    Optional<JWTToken> findByUserId(Long userId);

    Optional<JWTToken> findByToken(String token);

    void deleteByUserId(Long userId);

    // Cleanup expired tokens
    void deleteByExpireAtBefore(LocalDateTime time);
}
