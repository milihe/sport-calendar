package com.sportradar.demo.controller;

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

import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
public class AddEventController {
    private final static int unselectedValue = -1;

    @Autowired
    JdbcTemplate jdbc;

    @GetMapping("/add-event")
    public String addEvent(Model model,
                           @RequestParam(value = "sportId", required = false) String paramSportId) {

        initializeModelForInputForm(model, paramSportId, null, null, null, null);
        return "add-event";
    }

    @PostMapping("/add-event")
    public String addEvent(Model model,
                         @RequestParam(value = "sportId") String paramSportId,
                         @RequestParam(value = "start") String paramStart,
                         @RequestParam(value = "teamId1") String paramTeamId1,
                         @RequestParam(value = "teamId2") String paramTeamId2) {

        var eventRepository = new EventRepository(jdbc);
        try {
            var sportId = Integer.parseInt(paramSportId);

            if (paramStart.isEmpty()) {
                throw new IllegalArgumentException("Start is not selected");
            }
            var localStart = LocalDateTime.parse(paramStart);
            var start = localStart.atZone(ZoneId.of("Europe/Vienna")).toInstant();

            var teamId1 = Integer.parseInt(paramTeamId1);
            var teamId2 = Integer.parseInt(paramTeamId2);
            if (teamId1 == teamId2) {
                throw new IllegalArgumentException("Same team cannot be selected twice");
            }

            eventRepository.addEvent(start, sportId, teamId1, teamId2);
        } catch (Exception ex) {
            var error = ex.getMessage();
            initializeModelForInputForm(model, paramSportId, paramStart, paramTeamId1, paramTeamId2, error);
            return "add-event";
        }

        return "redirect:/";
    }

    private void initializeModelForInputForm(Model model,
                                        String sportId,
                                        String start,
                                        String teamId1,
                                        String teamId2,
                                        String error) {

        var sportRepository = new SportRepository(jdbc);
        var sports = sportRepository.getSports();
        // to populate the dropdown for selecting the sport
        model.addAttribute("sports", sports);

        int selectedSportId = parseIntegerParameter(sportId, unselectedValue);
        if (selectedSportId == unselectedValue) {
            selectedSportId = sports.getFirst().getSportId();
        }
        model.addAttribute("selectedSportId", selectedSportId);

        // to populate the dropdowns for selecting the teams
        var teamRepository = new TeamRepository(jdbc);
        var teams = teamRepository.getTeamsBySportId(selectedSportId);
        model.addAttribute("teams", teams);

        // if the insert is rejected (validation error),
        // we want to preserve the state of the form
        model.addAttribute("start", start);
        model.addAttribute("teamId1", teamId1);
        model.addAttribute("teamId2", teamId2);
        model.addAttribute("error", error);
    }

    private static int parseIntegerParameter(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

}