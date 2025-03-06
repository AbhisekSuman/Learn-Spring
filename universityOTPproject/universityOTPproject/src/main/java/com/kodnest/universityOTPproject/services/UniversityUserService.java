package com.kodnest.universityOTPproject.services;

import com.kodnest.universityOTPproject.entities.User;
import com.kodnest.universityOTPproject.repositories.UniversityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityUserService {

    @Autowired
    UniversityUserRepository repository;

    public String authenticate(String email, String password) {
        User user = repository.findByEmail(email);

        if (user == null) {
            return "Invalid Email or Password";
        } else {
            if (user.getPassword().equals(password)) {
                return user.getRole();
            } else {
                return "Invalid Email or Password";
            }
        }
    }
}
