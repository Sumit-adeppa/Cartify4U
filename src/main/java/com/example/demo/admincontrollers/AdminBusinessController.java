package com.example.demo.admincontrollers;

import com.example.demo.adminservices.AdminBusinessService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/business")
public class AdminBusinessController {

	private final AdminBusinessService adminBusinessService;

	public AdminBusinessController(AdminBusinessService adminBusinessService) {
		this.adminBusinessService = adminBusinessService;
	}

	@GetMapping("/monthly")
	public ResponseEntity<?> getMonthlyBusiness(@RequestParam int month,
			@RequestParam int year) {
		try {
			return ResponseEntity.ok(
					adminBusinessService.calculateMonthlyBusiness(month, year));
		} catch (Exception e) {
			Map<String, String> response = new HashMap<>();
			response.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/daily")
	public ResponseEntity<?> getDailyBusiness(@RequestParam String date) {
		try {
			LocalDate localDate = LocalDate.parse(date);
			return ResponseEntity.ok(
					adminBusinessService.calculateDailyBusiness(localDate));
		} catch (Exception e) {
			Map<String, String> response = new HashMap<>();
			response.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/yearly")
	public ResponseEntity<?> getYearlyBusiness(@RequestParam int year) {
		try {
			return ResponseEntity.ok(
					adminBusinessService.calculateYearlyBusiness(year));
		} catch (Exception e) {
			Map<String, String> response = new HashMap<>();
			response.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/overall")
	public ResponseEntity<?> getOverallBusiness() {
		try {
			return ResponseEntity.ok(
					adminBusinessService.calculateOverallBusiness());
		} catch (Exception e) {
			Map<String, String> response = new HashMap<>();
			response.put("error", e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
}
