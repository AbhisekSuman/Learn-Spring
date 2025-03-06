package com.kodnest.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/api")
public class Greet {
	
	@RequestMapping("/greet")
	@ResponseBody
	public String greetUser() {
		return "HELLO WORLD";
	}

	@RequestMapping("/data/{name}")
	@ResponseBody
	public String greetData(@PathVariable String name) {
		return "Hello " + name;
	}

	@RequestMapping("/display")
	@ResponseBody
	public String displayName(@RequestBody Map<String, String> input) {
		return "Hello " + input.get("username");
	}

	@RequestMapping("/home")
	public String home(@RequestParam String username, Model model) {
		String messageValue = "Hello " + username;
		model.addAttribute("message", messageValue);
		return "home";
	}
}
