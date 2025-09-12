package com.kodnest.salessavvy.services.adminservices;

import com.kodnest.salessavvy.dto.ProductRequest;
import com.kodnest.salessavvy.entities.Category;
import com.kodnest.salessavvy.entities.Product;
import com.kodnest.salessavvy.entities.ProductImage;
import com.kodnest.salessavvy.repository.CategoryRepository;
import com.kodnest.salessavvy.repository.ProductImageRepository;
import com.kodnest.salessavvy.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AdminProductService {

    ProductRepository productRepository;
    ProductImageRepository imageRepository;
    CategoryRepository categoryRepository;

    public AdminProductService(ProductRepository productRepository, ProductImageRepository imageRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product addProduct(ProductRequest productRequest) {
        Category category =  categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + productRequest.getCategoryId()));

        Product product = new Product(productRequest.getName(), productRequest.getDescription(), BigDecimal.valueOf(productRequest.getPrice()), productRequest.getStock(), category);
        product = productRepository.save(product);

        if (productRequest.getImageURL() != null && !productRequest.getImageURL().isEmpty()) {
            ProductImage image = new ProductImage(productRequest.getImageURL());
            image.setProduct(product);
            imageRepository.save(image);

        } else {
            throw new IllegalArgumentException("Image URL is invalid");
        }

        return product;
    }

    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
        imageRepository.deleteByProductId(productId);
    }
}
