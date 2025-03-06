package com.kodnest.demo.entities;

import jakarta.persistence.*;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private int otp;
    @Column
    private String role;

    public User() {
    }

    public User(int id, String name, String email, String password, int otp, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.otp = otp;
        this.role = role;
    }

    public User(String name, String email, String password, int otp, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.otp = otp;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
