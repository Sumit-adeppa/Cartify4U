package com.example.demo.adminservices;

import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.entity.*;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminBusinessService {

	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	public AdminBusinessService(OrderRepository orderRepository,
			ProductRepository productRepository,
			CategoryRepository categoryRepository) {
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	// Monthly
	public Map<String, Object> calculateMonthlyBusiness(int month, int year) {

		List<Order> orders = orderRepository.findByStatus(OrderStatus.SUCCESS);

		return calculateFilteredBusiness(orders, order -> order.getCreatedAt() != null &&
				order.getCreatedAt().getMonthValue() == month &&
				order.getCreatedAt().getYear() == year);
	}

	// Daily
	public Map<String, Object> calculateDailyBusiness(LocalDate date) {

		List<Order> orders = orderRepository.findByStatus(OrderStatus.SUCCESS);

		return calculateFilteredBusiness(orders, order -> order.getCreatedAt() != null &&
				order.getCreatedAt().toLocalDate().equals(date));
	}

	// Yearly
	public Map<String, Object> calculateYearlyBusiness(int year) {

		List<Order> orders = orderRepository.findByStatus(OrderStatus.SUCCESS);

		return calculateFilteredBusiness(orders, order -> order.getCreatedAt() != null &&
				order.getCreatedAt().getYear() == year);
	}

	// Overall
	public Map<String, Object> calculateOverallBusiness() {

		List<Order> orders = orderRepository.findByStatus(OrderStatus.SUCCESS);

		return calculateFilteredBusiness(orders, order -> true);
	}

	// Core reusable engine
	private Map<String, Object> calculateFilteredBusiness(List<Order> orders,
			java.util.function.Predicate<Order> filter) {

		double totalBusiness = 0.0;
		Map<String, Integer> categorySales = new HashMap<>();

		for (Order order : orders) {
			if (order == null)
				continue;

			if (!filter.test(order))
				continue;

			if (order.getTotalAmount() != null) {
				totalBusiness += order.getTotalAmount().doubleValue();
			}

			List<OrderItem> items = order.getOrderItems();
			if (items != null) {
				for (OrderItem item : items) {

					if (item.getProductId() == null)
						continue;

					Product product = productRepository.findByProductId(item.getProductId())
							.orElse(null);
					if (product == null)
						continue;

					String categoryName = product.getCategoryName();
					if (categoryName == null || categoryName.trim().isEmpty())
						continue;

					Integer quantity = item.getQuantity();
					if (quantity == null)
						quantity = 0;

					categorySales.put(categoryName,
							categorySales.getOrDefault(categoryName, 0)
							+ quantity);
				}
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("totalBusiness", totalBusiness);
		response.put("categorySales", categorySales);

		return response;
	}
}
