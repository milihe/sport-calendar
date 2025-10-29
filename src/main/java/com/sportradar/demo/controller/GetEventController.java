package com.sportradar.demo.controller;

import com.sportradar.demo.LocalDateTimeConverter;
import com.sportradar.demo.domain.Event;
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
public class GetEventController {

    @Autowired
    JdbcTemplate db;

    @GetMapping("/get-event")
    public String home(Model model,
                       @RequestParam(value = "eventId") String paramEventId) {

        var eventRepository = new EventRepository(db);
        var event = eventRepository.getEvent(Integer.parseInt(paramEventId));
        model.addAttribute("event", event);
        model.addAttribute("timeConverter", new LocalDateTimeConverter());
        return "get-event";
    }

}