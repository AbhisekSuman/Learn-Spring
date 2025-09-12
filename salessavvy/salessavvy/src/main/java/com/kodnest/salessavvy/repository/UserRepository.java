package com.kodnest.salessavvy.repository;

import com.kodnest.salessavvy.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);


    public Optional<User> findByEmail(String email);
}
