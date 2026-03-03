package com.example.demo.Controllers;

import com.example.demo.Services.OrderService;
import com.example.demo.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOrders(HttpServletRequest request) {

        User authenticatedUser = (User) request.getAttribute("authenticatedUser");

        if (authenticatedUser == null) {
            return ResponseEntity.status(401).build();
        }

        Long userId = authenticatedUser.getUserId();

        Map<String, Object> response = orderService.getOrdersForUser(userId);

        return ResponseEntity.ok(response);
    }
}
