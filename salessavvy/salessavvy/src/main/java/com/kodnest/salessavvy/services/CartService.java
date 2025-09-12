package com.kodnest.salessavvy.services;

import com.kodnest.salessavvy.entities.Cart;
import com.kodnest.salessavvy.entities.Product;
import com.kodnest.salessavvy.entities.ProductImage;
import com.kodnest.salessavvy.entities.User;
import com.kodnest.salessavvy.repository.CartRepository;
import com.kodnest.salessavvy.repository.ProductImageRepository;
import com.kodnest.salessavvy.repository.ProductRepository;
import com.kodnest.salessavvy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    CartRepository repository;
    ProductRepository productRepository;
    UserRepository userRepository;
    ProductImageRepository productImageRepository;


    public CartService(CartRepository repository, ProductRepository productRepository, UserRepository userRepository, ProductImageRepository productImageRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productImageRepository = productImageRepository;
    }

    public int getCartItemCount(int userId) {
        return repository.countTotalItems(userId);
    }

    public void addToCart(int userId, int productId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID" + userId));
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found with ID" + productId));

        Optional<Cart> optionalCart = repository.findByUserAndProduct(userId, productId);
        Cart cart;
        if (optionalCart.isPresent()) {
            cart = optionalCart.get();
            cart.setQuantity(cart.getQuantity() + 1);
        } else {
            cart = new Cart(user, product, quantity);
        }
        repository.save(cart);
    }

    public Map<String, Object> getCartItems(int userId) {
        List<Cart> cartList = repository.findCartItemWithProductDetails(userId);

        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID" + userId));
        response.put("username", user.getUsername());
        response.put("role", user.getRole().name());

        List<Map<String, Object>> productList = new ArrayList<>();
        double overallTotalPrice = 0;

        for (Cart cart: cartList) {
            Map<String, Object> products = new HashMap<>();
            Product product = cart.getProduct();

            List<ProductImage> images =  productImageRepository.findByProduct(product);
            String imageUrls = null;

            if (images != null && !images.isEmpty()) {
                imageUrls = images.get(0).getImageURL();
            } else {
                imageUrls = "default";
            }

            products.put("productId", product.getProductId());
            products.put("imageURL", imageUrls);
            products.put("name", product.getName());
            products.put("description", product.getDescription());
            products.put("product_price", product.getPrice());
            products.put("quantity", cart.getQuantity());
            products.put("total_price", cart.getQuantity() * product.getPrice().doubleValue());

            productList.add(products);
            overallTotalPrice = overallTotalPrice + cart.getQuantity() * product.getPrice().doubleValue();
        }

        Map<String, Object> cart = new HashMap<>();
        cart.put("overallTotalPrice", overallTotalPrice);
        cart.put("products", productList);

        response.put("cart", cart);
        return response;
    }

    public void updateCartItemQuantity(int userId, int productId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not exist with this ID: " + productId));

        Optional<Cart> cart = repository.findByUserAndProduct(userId, productId);

        if (cart.isPresent()) {
            Cart cartItem = cart.get();
            if (quantity == 0) {
                deleteCartItem(userId, productId);
            } else {
                cartItem.setQuantity(quantity);
                repository.save(cartItem);
            }
        }
    }

    public void deleteCartItem(int userId, int productId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not exist with this ID: " + productId));

        repository.deleteCartItem(userId, productId);
    }
}
