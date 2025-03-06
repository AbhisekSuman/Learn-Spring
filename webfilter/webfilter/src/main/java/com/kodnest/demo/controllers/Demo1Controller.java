package com.kodnest.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Demo1Controller {
    @RequestMapping("/res2")
    public String res2(@RequestParam String name, HttpServletRequest request) {
        String type = (String) request.getAttribute("userType" );
        return "Oye " + name + ". You are a " + type;
    }
}
