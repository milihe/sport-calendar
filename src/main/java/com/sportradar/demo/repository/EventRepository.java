package com.sportradar.demo.repository;

import com.sportradar.demo.Event;
import com.sportradar.demo.Sport;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EventRepository {
    private final JdbcTemplate jdbc;

    public EventRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Event> getEvents() {
        String sql = """
                SELECT eventId, start, s.sport, t1.team team1, t2.team team2
                FROM events e 
                JOIN sports s ON s.sportId = e.sportId
                JOIN teams t1 ON t1.teamId = e.teamId1
                JOIN teams t2 ON t2.teamId = e.teamId2
                ORDER BY start
                """;
        return jdbc.query(sql, this::mapRow);
    }

    private Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Event(
                rs.getInt("eventId"),
                rs.getTimestamp("start").toInstant(),
                rs.getString("sport"),
                rs.getString("team1"),
                rs.getString("team1")
        );
    }
}
