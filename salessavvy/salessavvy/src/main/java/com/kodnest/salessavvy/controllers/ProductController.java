package com.kodnest.salessavvy.controllers;

import com.kodnest.salessavvy.entities.Product;
import com.kodnest.salessavvy.entities.User;
import com.kodnest.salessavvy.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(originPatterns = "http://localhost:5173", allowCredentials = "true")
public class ProductController {

    ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProducts(@RequestParam String category, HttpServletRequest request) {

        try {
            User user = (User) request.getAttribute("authenticatedUser");

            if (user == null) {
                return ResponseEntity.status(401).body(Map.of("Error", ""));
            }

            List<Product> products = service.getProductsByCategory(category);

            Map<String, Object> response = new HashMap<>();

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("username", user.getUsername());
            userInfo.put("role", user.getRole().name());

            response.put("user", userInfo);

            List<Map<String, Object>> productList = new ArrayList<>();

            for (Product product : products) {
                Map<String, Object> productDetails = new HashMap<>();
                productDetails.put("productId", product.getProductId());
                productDetails.put("productName", product.getName());
                productDetails.put("description", product.getDescription());
                productDetails.put("price", product.getPrice());
                productDetails.put("stock", product.getStock());

                List<String> images = service.getImages(product);
                productDetails.put("productImages", images);

                productList.add(productDetails);
            }

            response.put("products", productList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("Error", e.getMessage()));
        }
    }
}
