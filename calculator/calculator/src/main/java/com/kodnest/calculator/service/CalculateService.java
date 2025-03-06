package com.kodnest.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculateService {

    public int add(int num1, int num2) {
        return num1 + num2;
    }

    public int sub(int num1, int num2) {
        return num1 - num2;
    }

    public int multiply(int num1, int num2) {
        return num1 * num2;
    }

    public double divide(int num1, int num2) {
        return (double) num1 / num2;
    }
}
