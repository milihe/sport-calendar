package com.sportradar.demo.controller;

import com.sportradar.demo.Event;
import com.sportradar.demo.Sport;
import com.sportradar.demo.repository.EventRepository;
import com.sportradar.demo.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    JdbcTemplate jdbc;

    @GetMapping("/")
    public String home(Model model) {
        var sportRepository = new SportRepository(jdbc);
        var sports = sportRepository.getSports();
        model.addAttribute("sports", sports);

        var eventRepository = new EventRepository(jdbc);
        var events = eventRepository.getEvents();

        model.addAttribute("events", events);
        return "home"; // This resolves to src/main/resources/templates/home.html
    }
}