package com.sportradar.demo.controller;

import com.sportradar.demo.Sport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        List<Sport> sports = List.of(
                new Sport(1, "handball"),
                new Sport(2, "football")
        );

        model.addAttribute("sports", sports);
        return "home"; // This resolves to src/main/resources/templates/home.html
    }
}