package com.sportradar.demo.controller;

import com.sportradar.demo.Event;
import com.sportradar.demo.repository.EventRepository;
import com.sportradar.demo.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private final static int unselectedValue = -1;

    @Autowired
    JdbcTemplate jdbc;

    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(value = "sportId", required = false) String paramSportId) {
        var selectedSportId = parseIntegerParameter(paramSportId, unselectedValue);

        var sportRepository = new SportRepository(jdbc);
        var sports = sportRepository.getSports();
        model.addAttribute("sports", sports);

        var eventRepository = new EventRepository(jdbc);
        List<Event> events;
        if (selectedSportId == unselectedValue) {
            events = eventRepository.getEvents();
        } else {
            events = eventRepository.getEventsBySport(selectedSportId);
        }
        model.addAttribute("events", events);
        return "home"; // This resolves to src/main/resources/templates/home.html
    }

    private static int parseIntegerParameter(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }
}