package com.kodnest.EmpoloyeeDataApp.Entity;


import jakarta.persistence.*;

@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private int salary;
    @Column
    private String designation;

    public Employee() {
    }

    public Employee(int id, String name, String email, int salary, String designation) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.designation = designation;
    }

    public Employee(String name, String email, int salary, String designation) {
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.designation = designation;
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
