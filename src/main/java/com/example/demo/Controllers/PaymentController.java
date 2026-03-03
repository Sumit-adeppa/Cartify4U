package com.example.demo.Controllers;

import com.example.demo.Services.PaymentService;
import com.example.demo.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Create Razorpay Order
     * @param requestBody Map containing totalAmount and cartItems
     * @param request HttpServletRequest for authenticated user
     * @return ResponseEntity with Razorpay Order ID
     */
    @PostMapping("/create")
    public ResponseEntity<String> createPaymentOrder(@RequestBody Map<String, Object> requestBody,
                                                     HttpServletRequest request) {
        try {
            User user = (User) request.getAttribute("authenticatedUser");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            BigDecimal totalAmount =
                    new BigDecimal(requestBody.get("totalAmount").toString());

            String razorpayOrderId =
                    paymentService.createOrder(user.getUserId(), totalAmount, null);

            return ResponseEntity.ok(razorpayOrderId);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating order: " + e.getMessage());
        }
    }

    /**
     * Verify Razorpay Payment
     * @param requestBody Map containing Razorpay payment details
     * @param request HttpServletRequest for authenticated user
     * @return ResponseEntity with success or failure message
     */
    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        try {
            // Fetch authenticated user
            User user = (User) request.getAttribute("authenticatedUser");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }
            Long userId=user.getUserId();
            // Extract Razorpay payment details from the request body
            String razorpayOrderId = (String) requestBody.get("razorpayOrderId");
            String razorpayPaymentId = (String) requestBody.get("razorpayPaymentId");
            String razorpaySignature = (String) requestBody.get("razorpaySignature");

            // Call the payment service to verify the payment
            boolean isVerified = paymentService.verifyPayment(razorpayOrderId, razorpayPaymentId, razorpaySignature,userId);

            if (isVerified) {
                return ResponseEntity.ok("Payment verified successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment verification failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error verifying payment: " + e.getMessage());
        }
    }
}