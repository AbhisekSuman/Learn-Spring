package com.kodnest.salessavvy.services;

import com.kodnest.salessavvy.entities.*;
import com.kodnest.salessavvy.repository.CartRepository;
import com.kodnest.salessavvy.repository.OrderItemRepository;
import com.kodnest.salessavvy.repository.OrderRepository;
import com.kodnest.salessavvy.repository.UserRepository;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Value("${razorpay.key_id}")
    private String razorpayKeyId;
    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;

    private OrderRepository orderRepository;
    private OrderItemRepository itemRepository;
    private CartRepository cartRepository;
    private UserRepository userRepository;


    public PaymentService(OrderRepository orderRepository, OrderItemRepository itemRepository, CartRepository cartRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String createOrder(int userId, BigDecimal totalAmount, List<OrderItem> cartItems) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User Not found"));
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        var orderRequest = new JSONObject();

        orderRequest.put("amount", totalAmount.multiply(BigDecimal.valueOf(100)).intValue());
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());
        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);

        Order order = new Order();
        order.setId(razorpayOrder.get("id"));
        order.setUser(user.getId());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(totalAmount);
        order.setCreateAt(LocalDateTime.now());
        orderRepository.save(order);

        return razorpayOrder.get("id");
    }

    @Transactional
    public boolean verifyPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature, int userId) {

        JSONObject object = new JSONObject();
        object.put("razorpay_order_id", razorpayOrderId);
        object.put("razorpay_payment_id", razorpayPaymentId);
        object.put("razorpay_signature", razorpaySignature);
        try {
            boolean isValid = com.razorpay.Utils.verifyPaymentSignature(object, razorpayKeySecret);

            if (isValid) {
                Order order = orderRepository.findById(razorpayOrderId).orElseThrow(() -> new IllegalArgumentException("Order Not Found"));
                order.setStatus(OrderStatus.SUCCESS);
                order.setUpdatedAt(LocalDateTime.now());
                orderRepository.save(order);

                List<Cart> cartItems = cartRepository.findCartItemWithProductDetails(userId);

                for (Cart cart: cartItems) {
                    OrderItem orderItem = new OrderItem();

                    orderItem.setOrder(order);
                    orderItem.setQuantity(cart.getQuantity());
                    orderItem.setProduct(cart.getProduct());
                    orderItem.setPricePerUnit(cart.getProduct().getPrice());
                    orderItem.setQuantity(cart.getQuantity());
                    orderItem.setTotalPrice(cart.getProduct().getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
                    itemRepository.save(orderItem);
                }

                cartRepository.deleteAllCartItemsById(userId);

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
                e.printStackTrace();
                return false;
        }
    }
}
