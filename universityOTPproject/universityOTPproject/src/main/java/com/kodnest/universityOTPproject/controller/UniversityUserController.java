package com.kodnest.universityOTPproject.controller;

import com.kodnest.universityOTPproject.services.UniversityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/api")
public class UniversityUserController {

    @Autowired
    UniversityUserService service;

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @PostMapping("/university/login")
    public String authenticate(@RequestParam String email, @RequestParam String password) {
//        String email = formData.get("email");
//        String password = formData.get("password");

        String response = service.authenticate(email, password);

        if (response.equalsIgnoreCase("student")) {
            return "StudentDashboard";
        } else if (response.equalsIgnoreCase("teacher")) {
            return "TeacherDashboard";
        } else {
            return "Error";
        }

    }
}

