package com.kodnest.salessavvy.dto;

public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private int categoryId;
    private int stock;
    private String imageURL;

    public ProductRequest() {
    }

    public ProductRequest(String name, String description, Double price, int categoryId, int stock, String imageURL) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.stock = stock;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
