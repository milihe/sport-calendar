package com.sportradar.demo.repository;

import com.sportradar.demo.domain.Team;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeamRepository {
    private final JdbcTemplate db;

    public TeamRepository(JdbcTemplate db) {
        this.db = db;
    }

    public List<Team> getTeamsBySportId(int sportId) {
        String sql = "SELECT * FROM teams WHERE sportId = ? ORDER BY team";
        var parameters = new Object[] {sportId};
        return db.query(sql, this::mapRow, parameters);
    }

    private Team mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Team(
                rs.getInt("teamId"),
                rs.getInt("sportId"),
                rs.getString("team")
        );
    }


}
