package com.kodnest.salessavvy.repository;

import com.kodnest.salessavvy.entities.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.product.productId = :productId")
    Optional<Cart> findByUserAndProduct(int userId, int productId);

    @Query("SELECT COALESCE(SUM(c.quantity),0) FROM Cart c WHERE c.user.id =  :userId")
    int countTotalItems(int userId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.product p LEFT JOIN FETCH ProductImage pi ON p.productId = pi.product.productId WHERE c.user.id= :userId")
    List<Cart> findCartItemWithProductDetails(int userId);

    @Modifying
    @Transactional
    @Query("UPDATE Cart c SET c.quantity = :quantity WHERE c.id = :cartId")
    public void updateCartItemQuantity(int cartId, int quantity);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.user.id = :userId AND c.product.productId = :productId")
    public void deleteCartItem(int userId, int productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.user.id = :userId")
    public void deleteAllCartItemsById(int userId);
}
