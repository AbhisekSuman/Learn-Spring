package com.kodnest.salessavvy.controllers;

import com.kodnest.salessavvy.dto.LoginRequest;
import com.kodnest.salessavvy.entities.User;
import com.kodnest.salessavvy.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(originPatterns = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/authentication")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            User user = service.authenticate(request.getUsername(), request.getPassword());

            String token = service.generateToken(user);

            Cookie cookie = new Cookie("authToken", token);
            cookie.setMaxAge(3600);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setDomain("localhost");
            response.addCookie(cookie);

            response.addHeader("Set-Cookie", String.format("authToken=%s; HttpOnly; Path=/; Max-Age=3600; SameSite=None", token));

            Map<String, String> responseData = new HashMap<>();

            responseData.put("message", "Login successfully");
            responseData.put("username", user.getUsername());
            responseData.put("role", user.getRole().name());

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response) {

        try {
            User user = (User) request.getAttribute("authenticatedUser");

            if (user == null) {
                throw new RuntimeException("User Not exist");
            }

            service.logout(user);

            Cookie cookie = new Cookie("authToken", null);
            cookie.setMaxAge(3600);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setDomain("localhost");
            response.addCookie(cookie);

            return ResponseEntity.ok(Map.of("message", "Logout Successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
        }
    }
}
