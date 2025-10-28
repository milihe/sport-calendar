package com.sportradar.demo.controller;

import com.sportradar.demo.AddEventForm;
import com.sportradar.demo.repository.EventRepository;
import com.sportradar.demo.repository.SportRepository;
import com.sportradar.demo.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddEventController {
    @Autowired
    JdbcTemplate db;

    @GetMapping("/add-event")
    public String addEvent(Model model,
                           @RequestParam(value = "sportId", required = false) String paramSportId) {

        var addEventForm = new AddEventForm(paramSportId);
        addEventForm.setupUIModel(model, new SportRepository(db), new TeamRepository(db), null);
        return "add-event";
    }

    @PostMapping("/add-event")
    public String addEvent(Model model,
                         @RequestParam(value = "sportId") String paramSportId,
                         @RequestParam(value = "start") String paramStart,
                         @RequestParam(value = "teamId1") String paramTeamId1,
                         @RequestParam(value = "teamId2") String paramTeamId2) {

        var addEventForm = new AddEventForm(paramSportId, paramStart, paramTeamId1, paramTeamId2);
        var validationErrors = addEventForm.getValidationErrors();
        if (validationErrors.isEmpty()) {
            var eventRepository = new EventRepository(db);
            eventRepository.addEvent(addEventForm.getStart(),
                    addEventForm.getSportId(),
                    addEventForm.getTeamId1(),
                    addEventForm.getTeamId2());
            // after the event was inserted to db, we redirect back to the page listing the events.
            return "redirect:/";
        } else {
            // validation failed, we show the form again
            addEventForm.setupUIModel(model, new SportRepository(db), new TeamRepository(db), validationErrors);
            return "add-event";
        }
    }

}