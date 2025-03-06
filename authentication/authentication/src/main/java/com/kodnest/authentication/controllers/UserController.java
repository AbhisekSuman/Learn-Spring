package com.kodnest.authentication.controllers;

import com.kodnest.authentication.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/user/login")
    public String authenticateUser(@RequestBody Map<String, String> request) {
        String name = request.get("username");
        String pw = request.get("password");

        String msg = service.authenticateUser(name, pw);

        if (msg.equalsIgnoreCase("error")) {
            return "Error Page";
        } else {
            return "Home Page";

        }
    }

//    for frontend
    @PostMapping("/auth/login")
    public Map<String, String> authenticate(@RequestBody Map<String, String> request, HttpServletResponse servletResponse) {
        try {
            String name = request.get("userName");
            String pw = request.get("password");

            String msg = service.authenticateUser(name, pw);

            if (msg.equalsIgnoreCase("error")) {
                Map<String, String> response = new HashMap<>();
                response.put("message", msg);
                servletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return response;
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("username", name);
                response.put("message", msg);
                servletResponse.setStatus(HttpServletResponse.SC_OK);
                return response;
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("Error", e.getMessage());
            servletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return response;
        }
    }

    @PostMapping("/auth/register")
    public String registerUser(@RequestBody Map<String, String> request, HttpServletResponse response) {
        String userName = request.get("username");
        String password = request.get("password");
        String role = request.get("role");


        if (service.saveUser(userName, password, role)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return "User Created";
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "Error";
        }
    }
}
