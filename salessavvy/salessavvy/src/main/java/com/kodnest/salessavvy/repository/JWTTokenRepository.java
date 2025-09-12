package com.kodnest.salessavvy.repository;

import com.kodnest.salessavvy.entities.JWTToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface JWTTokenRepository extends JpaRepository<JWTToken, Integer> {

    @Query("SELECT t FROM JWTToken t WHERE t.user.id = :userId")  // Fixed field reference
    JWTToken findByUserId(int userId);

    Optional<JWTToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM JWTToken t WHERE t.user.id = :userId")
    void deleteTokenByUserId(int userId);
}

