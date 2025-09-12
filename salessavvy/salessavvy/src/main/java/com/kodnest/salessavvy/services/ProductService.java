package com.kodnest.salessavvy.services;

import com.kodnest.salessavvy.entities.Category;
import com.kodnest.salessavvy.entities.Product;
import com.kodnest.salessavvy.entities.ProductImage;
import com.kodnest.salessavvy.repository.CategoryRepository;
import com.kodnest.salessavvy.repository.ProductImageRepository;
import com.kodnest.salessavvy.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductImageRepository imageRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    public List<Product> getProductsByCategory(String categoryName) throws RuntimeException {
        if (categoryName != null && !categoryName.isEmpty()) {
            Optional<Category> optionalCategory = categoryRepository.findBycategoryName(categoryName);

            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                return productRepository.findByCategory(category);
            } else {
                throw new RuntimeException("Category Not Found");
            }
        } else {
            return productRepository.findAll();
        }
    }

    public List<String> getImages(Product productId) throws RuntimeException {
        List<ProductImage> images = imageRepository.findByProduct(productId);
        List<String> imageURls = new ArrayList<>();

        for (ProductImage image: images) {
            imageURls.add(image.getImageURL());
        }
        return imageURls;
    }
}
