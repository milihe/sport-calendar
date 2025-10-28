package com.sportradar.demo.domain;

import com.sportradar.demo.LocalDateTimeConverter;
import com.sportradar.demo.repository.SportRepository;
import com.sportradar.demo.repository.TeamRepository;
import org.springframework.ui.Model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AddEventForm {
    private int sportId;
    private String localStart;
    private Instant start;
    private int teamId1;
    private int teamId2;

    public AddEventForm(String paramSportId) {
        sportId = tryParseInteger(paramSportId, 0);
    }

    public AddEventForm(String paramSportId, String paramLocalStart, String paramTeamId1, String paramTeamId2) {
        sportId = tryParseInteger(paramSportId, 0);
        localStart = paramLocalStart;
        var timeConverter = new LocalDateTimeConverter();
        start = timeConverter.tryGetInstantFromLocalDateTime(paramLocalStart);
        teamId1 = tryParseInteger(paramTeamId1, 0);
        teamId2 = tryParseInteger(paramTeamId2, 0);
    }

    public int getSportId() {
        return sportId;
    }

    public Instant getStart() {
        return start;
    }

    public int getTeamId1() {
        return teamId1;
    }

    public int getTeamId2() {
        return teamId2;
    }

    public List<String> getValidationErrors() {
        var errors = new ArrayList<String>();
        if (sportId == 0) {
            errors.add("Sport is not selected.");
        }
        if (start == null) {
            errors.add("Start is not selected.");
        }
        if (teamId1 == 0) {
            errors.add("Team1 is not selected");
        }
        if (teamId2 == 0) {
            errors.add("Team2 is not selected");
        }
        if (teamId1 > 0 && teamId1 == teamId2) {
            errors.add("Same team cannot be selected twice");
        }
        return errors;
    }

    public void setupUIModel(Model model, SportRepository sportRepository, TeamRepository teamRepository, List<String> errors) {
        // To populate the sport selector dropdown list
        var sport = sportRepository.getSports();
        model.addAttribute("sports", sport);

        // To populate the team selector dropdown list
        if (sportId == 0) {
            // When navigating to the add event page, the sportId will be null,
            // in that case we are going to autoselect the first sport, so we can populate the available teams accordingly.
            sportId = sport.getFirst().getSportId();
        }
        var teams = teamRepository.getTeamsBySportId(sportId);
        model.addAttribute("teams", teams);

        // In case the validation fails for adding event, and form gets displayed again,
        // we will send back the current values, so we can restore the state of inputs, dropdowns
        model.addAttribute("selectedSportId", sportId);
        model.addAttribute("start", localStart);
        model.addAttribute("teamId1", teamId1);
        model.addAttribute("teamId2", teamId2);

        if (errors != null && errors.size() > 0) {
            model.addAttribute("error", String.join(" ",errors));
        }

    }

    private int tryParseInteger(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }
}
