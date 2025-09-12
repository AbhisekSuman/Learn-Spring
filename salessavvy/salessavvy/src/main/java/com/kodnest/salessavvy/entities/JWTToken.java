package com.kodnest.salessavvy.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "jwt_tokens")
public class JWTToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Fixed field name

    @Column(nullable = false)
    private String token;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expires;  // Fixed variable name and type
    
    public JWTToken() {
    }

    public JWTToken(User user, String token, LocalDateTime createdAt, LocalDateTime expires) {
        this.user = user;
        this.token = token;
        this.createdAt = createdAt;
        this.expires = expires;
    }

    public JWTToken(int id, User user, String token, LocalDateTime createdAt, LocalDateTime expires) {
        this.id = id;
        this.user = user;
        this.token = token;
        this.createdAt = createdAt;
        this.expires = expires;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getuser() {
        return user;
    }

    public void setuser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        return "JWTToken{" +
                "user=" + user +
                ", token='" + token + '\'' +
                ", createdAt=" + createdAt +
                ", expires=" + expires +
                '}';
    }
}
