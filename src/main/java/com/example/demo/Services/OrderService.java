package com.example.demo.Services;

import java.util.Map;

public interface OrderService {

    Map<String, Object> getOrdersForUser(Long userId);

}
