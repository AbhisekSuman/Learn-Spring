package com.kodnest.salessavvy.controllers;

import com.kodnest.salessavvy.entities.User;
import com.kodnest.salessavvy.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/user")
public class UserController {

    UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            user = service.registration(user);
            return ResponseEntity.ok(Map.of("message", "User registered successfully", "username", user.getUsername()));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
