package com.sds.ehdsi;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OptOutRepository {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/db/optout.db";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public OptOutRepository() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // Create opt_out table
            String createOptOutTableSQL = """
                CREATE TABLE IF NOT EXISTS opt_out (
                    id TEXT PRIMARY KEY,
                    opt_out_time TEXT NOT NULL
                )
                """;
            try (PreparedStatement stmt = conn.prepareStatement(createOptOutTableSQL)) {
                stmt.execute();
            }

            // Create eligibility_queries table
            String createQueriesTableSQL = """
                CREATE TABLE IF NOT EXISTS eligibility_queries (
                    id TEXT NOT NULL,
                    query_time TEXT NOT NULL
                )
                """;
            try (PreparedStatement stmt = conn.prepareStatement(createQueriesTableSQL)) {
                stmt.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public void saveOptOut(String id) {
        String sql = "INSERT OR REPLACE INTO opt_out (id, opt_out_time) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(2, LocalDateTime.now().format(FORMATTER));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save opt-out", e);
        }
    }

    public boolean isEligible(String id) {
        logEligibilityQuery(id); // Log every query

        String sql = "SELECT 1 FROM opt_out WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check eligibility", e);
        }
    }

    private void logEligibilityQuery(String id) {
        String sql = "INSERT INTO eligibility_queries (id, query_time) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(2, LocalDateTime.now().format(FORMATTER));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to log eligibility query", e);
        }
    }
}
