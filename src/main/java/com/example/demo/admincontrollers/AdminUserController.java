package com.example.demo.admincontrollers;

import com.example.demo.adminservices.AdminUserService;
import com.example.demo.entity.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyUser(@RequestBody Map<String, Object> request) {

        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            String username = (String) request.get("username");
            String email = (String) request.get("email");
            String role = (String) request.get("role");

            User updatedUser =
                    adminUserService.modifyUser(userId, username, email, role);

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/getbyid")
    public ResponseEntity<?> getUserById(@RequestBody Map<String, Long> userRequest) {
    	 try {
             Long userId = userRequest.get("userId");
             User user = adminUserService.getUserById(userId);
             return ResponseEntity.status(HttpStatus.OK).body(user);
         } catch (IllegalArgumentException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
         }
     }
}
