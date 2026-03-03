package com.example.demo.ServicesImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.OrderItemRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Services.PaymentService;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.Product;
import com.example.demo.sequence.SequenceGeneratorService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.key_id}")
    private String razorpayKeyId;

    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final SequenceGeneratorService sequenceGenerator;

    public PaymentServiceImpl(OrderRepository orderRepository,
                              CartRepository cartRepository,
                              ProductRepository productRepository,
                              OrderItemRepository orderItemRepository,
                              SequenceGeneratorService sequenceGenerator) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    // 🔹 CREATE ORDER
    @Override
    @Transactional
    public String createOrder(Long userId, BigDecimal totalAmount, List<OrderItem> cartItems)
            throws RazorpayException {

        RazorpayClient razorpayClient =
                new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", totalAmount.multiply(BigDecimal.valueOf(100)).intValue());
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

        com.razorpay.Order razorpayOrder =
                razorpayClient.orders.create(orderRequest);

        Order order = new Order();
        order.setOrderId(sequenceGenerator.generateSequence(Order.SEQUENCE_NAME));
        order.setRazorpayOrderId(razorpayOrder.get("id"));
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);

        return razorpayOrder.get("id");
    }

    
    @Override
    @Transactional
    public boolean verifyPayment(String razorpayOrderId,
                                 String razorpayPaymentId,
                                 String razorpaySignature,
                                 Long userId) {

        try {
            JSONObject attributes = new JSONObject();
            attributes.put("razorpay_order_id", razorpayOrderId);
            attributes.put("razorpay_payment_id", razorpayPaymentId);
            attributes.put("razorpay_signature", razorpaySignature);

            boolean isSignatureValid =
                    com.razorpay.Utils.verifyPaymentSignature(attributes, razorpayKeySecret);

            if (!isSignatureValid) {
                System.out.println("Signature verification failed");
                return false;
            }

            // 🔹 Find order by razorpayOrderId
            Order order = orderRepository.findByRazorpayOrderId(razorpayOrderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            // 🔹 Update order status
            order.setStatus(OrderStatus.SUCCESS);
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);

            // 🔹 Fetch cart items (CartItem has ONLY productId)
            List<CartItem> cartItems = cartRepository.findByUserId(userId);

            for (CartItem cartItem : cartItems) {

                //  FETCH PRODUCT MANUALLY (NO ENTITY CHANGE)
                Product product = productRepository.findByProductId(cartItem.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                BigDecimal price = product.getPrice();   //  CORRECT PRICE SOURCE

                OrderItem item = new OrderItem();
                item.setOrderItemId(sequenceGenerator.generateSequence(OrderItem.SEQUENCE_NAME));
                item.setOrderId(order.getOrderId());
                item.setProductId(product.getProductId());
                item.setQuantity(cartItem.getQuantity());
                item.setPricePerUnit(price);
                item.setTotalPrice(
                        price.multiply(BigDecimal.valueOf(cartItem.getQuantity()))
                );

                orderItemRepository.save(item);
            }

            // 🔹 Clear cart
            cartRepository.deleteByUserId(userId);

            System.out.println("Payment verified, order completed");

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
