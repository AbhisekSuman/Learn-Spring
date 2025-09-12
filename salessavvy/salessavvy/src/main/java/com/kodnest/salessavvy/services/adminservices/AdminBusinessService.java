package com.kodnest.salessavvy.services.adminservices;

import com.kodnest.salessavvy.repository.OrderItemRepository;
import com.kodnest.salessavvy.repository.OrderRepository;
import com.kodnest.salessavvy.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class AdminBusinessService {

    OrderRepository orderRepository;
    OrderItemRepository itemRepository;
    ProductRepository productRepository;

    public AdminBusinessService(OrderRepository orderRepository, OrderItemRepository itemRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }

    public Map<String, Object> getDailyBusiness(LocalDate date) {
        return null;
    }


    public Map<String, Object> getMonthlyBusiness(LocalDate date) {
        return null;
    }

    public Map<String, Object> getYearlyBusiness(LocalDate date) {
        return null;
    }

    public Map<String, Object> getOverallBusiness(LocalDate date) {
        return null;
    }
}
