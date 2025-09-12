package com.kodnest.salessavvy.services;

import com.kodnest.salessavvy.entities.OrderItem;
import com.kodnest.salessavvy.entities.Product;
import com.kodnest.salessavvy.entities.ProductImage;
import com.kodnest.salessavvy.entities.User;
import com.kodnest.salessavvy.repository.OrderItemRepository;
import com.kodnest.salessavvy.repository.ProductImageRepository;
import com.kodnest.salessavvy.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private OrderItemRepository orderItemRepository;
    private ProductRepository productRepository;
    private ProductImageRepository imageRepository;

    public OrderService(OrderItemRepository orderItemRepository, ProductRepository productRepository, ProductImageRepository imageRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
    }

    public Map<String, Object> getOrderesOfUser(User user) {
        List<OrderItem> orderItems = orderItemRepository.findSuccessfullOrderByUserId(user.getId());

        Map<String, Object> response = new HashMap<>();

        response.put("username", user.getUsername());
        response.put("role", user.getRole());

        List<Map<String, Object>> products = new ArrayList<>();

        for (OrderItem item: orderItems) {
            Product product  = productRepository.findById(item.getProduct().getProductId()).orElseThrow(() -> new IllegalArgumentException("Product Not Found"));

            List<ProductImage> images = imageRepository.findByProduct(product);
            String imageURL  = images.get(0).getImageURL();

            Map<String, Object> productDetails = new HashMap<>();

            productDetails.put("order_id", item.getOrder().getId());
            productDetails.put("quantity", item.getQuantity());
            productDetails.put("total_price", item.getTotalPrice());
            productDetails.put("image_url", imageURL);
            productDetails.put("product_id", product.getProductId());
            productDetails.put("name", product.getName());
            productDetails.put("description", product.getDescription());
            productDetails.put("price_per_unit", item.getPricePerUnit());

            products.add(productDetails);
        }

        response.put("products", products);
        return response;
    }
}
