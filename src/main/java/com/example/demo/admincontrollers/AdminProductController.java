package com.example.demo.admincontrollers;

import com.example.demo.adminservices.AdminProductService;
import com.example.demo.entity.Product;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

	private final AdminProductService adminProductService;

	public AdminProductController(AdminProductService adminProductService) {
		this.adminProductService = adminProductService;
	}

	@PostMapping("/add")
	public ResponseEntity<?> addProduct(@RequestBody Map<String, Object> request) {

		try {
			String name = (String) request.get("name");
			String description = (String) request.get("description");
			Double price = Double.valueOf(request.get("price").toString());
			Integer stock = Integer.valueOf(request.get("stock").toString());
			Long categoryId = Long.valueOf(request.get("categoryId").toString());
			String imageUrl = (String) request.get("imageUrl");

			Product product = adminProductService.addProductWithImage(
					name, description, price, stock, categoryId, imageUrl
					);

			return ResponseEntity.status(HttpStatus.CREATED).body(product);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteProduct(@RequestBody Map<String, Long> request) {

		try {
			Long productId = Long.valueOf(request.get("productId").toString());
			adminProductService.deleteProduct(productId);
			return ResponseEntity.ok("Product deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
