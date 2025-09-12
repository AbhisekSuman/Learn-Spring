package com.kodnest.salessavvy.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "productimages")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer imageId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "image_url")
    private String imageURL;

    public ProductImage() {
    }

    public ProductImage(Integer imageId, Product product, String imageURL) {
        this.imageId = imageId;
        this.product = product;
        this.imageURL = imageURL;
    }

    public ProductImage(String imageURL) {
        this.imageURL = imageURL;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
