package com.kodnest.salessavvy.admincontrollers;

import com.kodnest.salessavvy.entities.User;
import com.kodnest.salessavvy.services.adminservices.AdminUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/user")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AdminUserController {

    AdminUserService service;

    public AdminUserController(AdminUserService service) {
        this.service = service;
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUserById(@RequestBody Map<String, Object> request) {
        try {
            int userId = (int) request.get("userId");
            User user = service.findUserById(userId);
            return ResponseEntity.ok(Map.of("user", user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserById(@RequestBody Map<String, Object> request) {
       try {
           int userId = (int) request.get("userId");

           service.deleteUserById(userId);
           return ResponseEntity.ok("User Deleted Successfully");
       } catch (IllegalArgumentException e ){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
       }
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyUserById(@RequestBody Map<String, Object> request) {
        try {
            int userId = (int) request.get("userId");
            String name = (String) request.get("username");
            String email = (String) request.get("email");
            String role = (String) request.get("role");

            User updatedUser = service.modifyUser(userId, name, email, role);

            return ResponseEntity.ok(Map.of("message", "User Updated Successfully", "user", updatedUser));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}
