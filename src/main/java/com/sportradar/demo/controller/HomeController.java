package com.sportradar.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("message", "Hello from Thymeleaf!");
        return "home"; // This resolves to src/main/resources/templates/home.html
    }
}