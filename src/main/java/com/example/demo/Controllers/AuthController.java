package com.example.demo.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Services.AuthService;
import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
                                   HttpServletResponse response) {

        try {
            User user = authService.authenticate(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            String token = authService.generateToken(user);

            Cookie cookie = new Cookie("authToken", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(3600);

            response.addCookie(cookie);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful");
            responseBody.put("username", user.getUsername());
            responseBody.put("role", user.getRole().name());
            responseBody.put("userId", user.getUserId());

            return ResponseEntity.ok(responseBody);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request,
                                                      HttpServletResponse response) {
        try {
            User user = (User) request.getAttribute("authenticatedUser");
            authService.logout(user);

            Cookie cookie = new Cookie("authToken", null);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseEntity.ok(Map.of("message", "Logout successful"));

        } catch (RuntimeException e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Logout failed"));
        }
    }
}
