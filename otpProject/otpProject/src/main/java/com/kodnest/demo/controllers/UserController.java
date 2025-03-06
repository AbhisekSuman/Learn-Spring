package com.kodnest.demo.controllers;

import com.kodnest.demo.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    HttpSession session;


    @RequestMapping("/")
    public String home() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        String msg = service.login(email, password);

        if (msg.equalsIgnoreCase("success")) {
            session.setAttribute("email", email);
            return "otpInput";
        } else {
            return "loginFail";
        }
    }

    @RequestMapping("/verify")
    public String verification(@RequestParam int otp,  Model model) {
        String email = (String) session.getAttribute("email");

        String res = service.verifyOTP(otp, email);
        if (res.equalsIgnoreCase("admin")) {
            model.addAttribute("role", "Admin Dashboard");
            return "UserDashboard";
        } else if (res.equalsIgnoreCase("student")) {
            model.addAttribute("role", "Student Dashboard");
            return "UserDashboard";
        } else {
            return "loginFail";
        }
    }
}
