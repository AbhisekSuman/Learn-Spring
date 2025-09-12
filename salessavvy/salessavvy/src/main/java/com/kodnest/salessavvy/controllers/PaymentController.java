package com.kodnest.salessavvy.controllers;

import com.kodnest.salessavvy.entities.OrderItem;
import com.kodnest.salessavvy.entities.Product;
import com.kodnest.salessavvy.entities.User;
import com.kodnest.salessavvy.repository.ProductRepository;
import com.kodnest.salessavvy.repository.UserRepository;
import com.kodnest.salessavvy.services.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService service;
    private UserRepository userRepository;
    private final ProductRepository productRepository;

    public PaymentController(PaymentService service, UserRepository userRepository, ProductRepository productRepository) {
        this.service = service;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPaymentOrder(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {

        try {
            User user = (User) request.getAttribute("authenticatedUser");

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User unauthorized");
            }

            BigDecimal totalAmount = new BigDecimal(requestBody.get("totalAmount").toString());
            List<Map<String, Object>> cartItems = (List<Map<String, Object>>) requestBody.get("cartItems");

            List<OrderItem> orderItems = new ArrayList<>();

            for (Map<String, Object> item : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(productRepository.findById((Integer) item.get("productId")).orElseThrow(() -> new RuntimeException("Product not found")));
                orderItem.setQuantity((Integer) item.get("quantity"));

                BigDecimal pricePerUnit = new BigDecimal(item.get("price").toString());
                orderItem.setPricePerUnit(pricePerUnit);
                orderItem.setTotalPrice(pricePerUnit.multiply(BigDecimal.valueOf((Integer) item.get("quantity"))));
                orderItems.add(orderItem);
            }

            String razorpayOrderId = service.createOrder(user.getId(), totalAmount, orderItems);

            return ResponseEntity.ok(razorpayOrderId);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        try {
            User user = (User) request.getAttribute("authenticatedUser");

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User unauthorized");
            }

            int userId = user.getId();

            String razorpayOrderId = (String) requestBody.get("razorpayOrderId");
            String razorpayPaymentId = (String) requestBody.get("razorpayPaymentId");
            String razorpaySignature = (String) requestBody.get("razorpaySignature");

            boolean isVerified = service.verifyPayment(razorpayOrderId, razorpayPaymentId, razorpaySignature, userId);

            if (isVerified) {
                return ResponseEntity.ok("Payment Successfull");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
