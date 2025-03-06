package com.kodnest.springSecurityDemo.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerDemo {

    @RequestMapping("/public")
    public String publicPage() {
        return "This is a public page accessible by anyone";
    }

    @RequestMapping("/admin")
    public String adminPage() {
        return "This is an Admin Page, accessible only to authenticated users.";
    }
}
