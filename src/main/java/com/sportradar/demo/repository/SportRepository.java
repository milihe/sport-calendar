package com.sportradar.demo.repository;

import com.sportradar.demo.domain.Sport;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SportRepository {
    private final JdbcTemplate jdbc;

    public SportRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Sport> getSports() {
        return jdbc.query("SELECT * FROM sports ORDER BY sport", this::mapRow);
    }

    private Sport mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Sport(
           rs.getInt("sportId"), rs.getString("sport")
        );
    }
}
