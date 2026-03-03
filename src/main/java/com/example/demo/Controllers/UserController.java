package com.example.demo.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Services.UserService;
import com.example.demo.entity.User;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);

            Map<String, Object> userData = new HashMap<>();
            userData.put("id", registeredUser.getUserId());
            userData.put("username", registeredUser.getUsername());
            userData.put("password", registeredUser.getPassword());
            userData.put("email", registeredUser.getEmail());
            userData.put("role", registeredUser.getRole().toString());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("user", userData);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
