package com.kodnest.salessavvy.repository;

import com.kodnest.salessavvy.entities.Category;
import com.kodnest.salessavvy.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);
}
