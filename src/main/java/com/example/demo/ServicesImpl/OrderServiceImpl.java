package com.example.demo.ServicesImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.Repository.OrderItemRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.ProductImageRepository;
import com.example.demo.Repository.UserRepository;

import com.example.demo.Services.OrderService;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductImage;
import com.example.demo.entity.User;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductImageRepository productImageRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            OrderItemRepository orderItemRepository,
                            ProductImageRepository productImageRepository,
                            UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.productImageRepository = productImageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, Object> getOrdersForUser(Long userId) {

        List<Order> orders =
                orderRepository.findByUserIdAndStatus(userId, OrderStatus.SUCCESS);

        List<Map<String, Object>> products = new ArrayList<>();

        for (Order order : orders) {

            List<OrderItem> items =
                    orderItemRepository.findByOrderId(order.getOrderId());

            for (OrderItem item : items) {

                Product product =
                        productRepository.findByProductId(item.getProductId())
                        .orElse(null);

                if (product == null) continue;

                //FETCH PRODUCT IMAGE CORRECTLY
                List<ProductImage> images =
                        productImageRepository.findByProductId(product.getProductId());

                String imageUrl = null;
                if (!images.isEmpty()) {
                    imageUrl = images.get(0).getImageUrl(); // first image
                }

                Map<String, Object> map = new HashMap<>();
                map.put("order_id", order.getOrderId());
                map.put("product_id", product.getProductId());
                map.put("name", product.getName());
                map.put("description", product.getDescription());
                map.put("price_per_unit", item.getPricePerUnit());
                map.put("quantity", item.getQuantity());
                map.put("total_price", item.getTotalPrice());

                // 🔥 CORRECT IMAGE KEY
                map.put("image_url", imageUrl);

                products.add(map);
            }
        }

        // 🔥 FETCH USERNAME
        User user = userRepository.findByUserId(userId).orElse(null);
        String username = user != null ? user.getUsername() : "Guest";

        Map<String, Object> response = new HashMap<>();
        response.put("user_id", userId);
        response.put("username", username);   // 🔥 send username
        response.put("products", products);

        return response;
    }
}
