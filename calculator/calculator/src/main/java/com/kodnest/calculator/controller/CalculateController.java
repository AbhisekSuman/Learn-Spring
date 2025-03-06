package com.kodnest.calculator.controller;

import com.kodnest.calculator.service.CalculateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculateController {
    CalculateService service;
    public CalculateController(CalculateService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/calculate")
    public String calculate(@RequestParam("operation") String operation, @RequestParam("num1") int n1, @RequestParam("num2") int n2, Model model) {

        String result = "";

        switch (operation) {
            case "add" -> result = "Addition Result is: " + service.add(n1, n2);
            case "subtract" -> result = "Subtraction Result is: " + service.sub(n1, n2);
            case "multiply" -> result = "Multiplication Result is: " + service.multiply(n1, n2);
            case "divide" -> result = "Division Result is: " + service.divide(n1, n2);
            default -> {
                model.addAttribute("error", "Operation is Invalid");
                return "result";
            }
        }

        model.addAttribute("result", result);
        return "result";
    }
}
