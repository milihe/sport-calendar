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
public class AddEventController {
    @Autowired
    JdbcTemplate jdbc;

    @GetMapping("/add-event")
    public String addEvent(Model model){
        return "add-event";
    }

}