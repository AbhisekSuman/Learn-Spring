package com.kodnest.salessavvy.controllers;

import com.kodnest.salessavvy.entities.User;
import com.kodnest.salessavvy.repository.UserRepository;
import com.kodnest.salessavvy.services.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CartController {

    CartService service;
    UserRepository repository;

    public CartController(CartService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/items/count")
    public ResponseEntity<Integer> getCartItemCount(@RequestParam String username) {
        User user = repository.findByUsername(username);
        if (user != null) {
            int count = service.getCartItemCount(user.getId());
            return ResponseEntity.ok(count);
        } else {
            throw new IllegalArgumentException("User not found with " + username);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        System.out.println("Username" + username);
        int productId = (int) request.get("productId");
        System.out.println("productId" + productId);
        int quantity = request.containsKey("quantity")? (int) request.get("quantity") : 1;

        User user = repository.findByUsername(username);
        if (user != null) {
            service.addToCart(user.getId(), productId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else {
            throw new IllegalArgumentException("User not found with " + username);
        }
    }

    @GetMapping("/items")
    public ResponseEntity<Map<String, Object>> getCartItems(HttpServletRequest request) {
        User user = (User) request.getAttribute("authenticatedUser");

        Map<String, Object> cartItems = service.getCartItems(user.getId());
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCartItemQuantity(@RequestBody Map<String, Object> request) {

        String username = (String) request.get("username");
        int productId = (int) request.get("productId");
        int quantity = request.containsKey("quantity")? (int) request.get("quantity") : 1;

        User user = repository.findByUsername(username);

        if (user != null) {
            service.updateCartItemQuantity(user.getId(), productId, quantity);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            throw new IllegalArgumentException("User not found with username: " + username);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCartItem(@RequestBody Map<String, Object> request) {

        String username = (String) request.get("username");
        int productId = (int) request.get("productId");

        User user = repository.findByUsername(username);

        if (user != null) {
            service.deleteCartItem(user.getId(), productId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new IllegalArgumentException("User not found with username: " + username);
        }
    }
}
