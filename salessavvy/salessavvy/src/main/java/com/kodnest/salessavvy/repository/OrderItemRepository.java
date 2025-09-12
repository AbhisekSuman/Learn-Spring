package com.kodnest.salessavvy.repository;

import com.kodnest.salessavvy.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId")
    List<OrderItem> findByOrderId(String orderId);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.user = :userId AND oi.order.status = SUCCESS")
    List<OrderItem> findSuccessfullOrderByUserId(int userId);
}
