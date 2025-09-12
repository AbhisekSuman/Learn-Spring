package com.kodnest.salessavvy.admincontrollers;

import com.kodnest.salessavvy.services.adminservices.AdminProductService;
import com.kodnest.salessavvy.dto.ProductRequest;
import com.kodnest.salessavvy.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    AdminProductService service;

    public AdminProductController(AdminProductService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Map<String, Object> request) {
        try {
            String name = (String) request.get("name");
            String description = (String) request.get("description");
            Double price = Double.valueOf(String.valueOf(request.get("price")));
            int stock = (int) request.get("stock");
            int categoryId = (int) request.get("category");
            String imageURL = (String) request.get("imageURL");

            Product addedProduct = service.addProduct(new ProductRequest(name, description, price,categoryId, stock, imageURL));

            Map<String, Object> response = new HashMap<>();
            response.put("product", addedProduct);
            response.put("imageURL", imageURL);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProduct(@RequestBody Map<String, Object> request) {
        try {
            int productId = (int) request.get("productId");
            service.deleteProduct(productId);
            return ResponseEntity.ok("Product Deleted Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went Wrong");
        }
    }
}
