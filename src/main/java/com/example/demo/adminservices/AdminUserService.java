package com.example.demo.adminservices;

import com.example.demo.Repository.JWTTokenRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.entity.Role;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminUserService {

    private final UserRepository userRepository;
    private final JWTTokenRepository jwtTokenRepository;

    public AdminUserService(UserRepository userRepository,
                            JWTTokenRepository jwtTokenRepository) {
        this.userRepository = userRepository;
        this.jwtTokenRepository = jwtTokenRepository;
    }

    @Transactional
    public User modifyUser(Long userId, String username, String email, String role) {

        User existingUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (username != null && !username.isEmpty()) {
            existingUser.setUsername(username);
        }

        if (email != null && !email.isEmpty()) {
            existingUser.setEmail(email);
        }

        if (role != null && !role.isEmpty()) {
            existingUser.setRole(Role.valueOf(role.toUpperCase()));
        }

        // 🔥 Force logout by deleting token
        jwtTokenRepository.deleteByUserId(userId);

        return userRepository.save(existingUser);
    }

    public User getUserById(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
