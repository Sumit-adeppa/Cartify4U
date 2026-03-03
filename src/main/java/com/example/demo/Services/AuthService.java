package com.example.demo.Services;

import com.example.demo.entity.User;

public interface AuthService {

    User authenticate(String username, String password);

    String generateToken(User user);

    void saveToken(User user, String token);

    void logout(User user);

    boolean validateToken(String token);

    String extractUsername(String token);

}
