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

    UserService userService;

    @Autowired
    HttpSession session;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String home() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        if (userService.login(email, password)) {
            session.setAttribute("email", email);
            return "otpInput";
        } else {
            return "loginFail";
        }
    }

    @RequestMapping("/verify")
    public String verify(@RequestParam int otp, Model model) {
        String email = (String) session.getAttribute("email");
        if (userService.verify(otp, email).equalsIgnoreCase("admin")) {
            model.addAttribute("role", "Admin Dashboard");
            return "UserDashboard";
        } else if (userService.verify(otp, email).equalsIgnoreCase("student")) {
            model.addAttribute("role", "Student Dashboard");
            return "UserDashboard";
        } else {
            return "loginFail";
        }
    }
}
