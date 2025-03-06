package com.kodnest.servicelayer.service;

import org.springframework.stereotype.Service;

@Service
public class ClientService {

    public String getFullName(String firstName, String lastName) {
        return "Full Name: " + firstName + " " + lastName;
    }
}
