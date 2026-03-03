package com.example.demo.Services;

import java.math.BigDecimal;
import java.util.List;

import com.example.demo.entity.OrderItem;
import com.razorpay.RazorpayException;

public interface PaymentService {

    String createOrder(Long userId, BigDecimal totalAmount, List<OrderItem> cartItems)
            throws RazorpayException;

    boolean verifyPayment(String razorpayOrderId,
                          String razorpayPaymentId,
                          String razorpaySignature,
                          Long userId);
}
