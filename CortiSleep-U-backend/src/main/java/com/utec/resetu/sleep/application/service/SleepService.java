package com.utec.resetu.sleep.application.service;

import com.utec.resetu.sleep.application.dto.SleepEntryRequest;
import com.utec.resetu.sleep.application.dto.SleepEntryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import com.utec.resetu.shared.exception.BusinessException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SleepService {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SleepEntryResponse> rowMapper = (rs, rowNum) -> SleepEntryResponse.builder()
            .id(rs.getLong("id"))
            .userId(rs.getLong("user_id"))
            .sleepAt(rs.getTimestamp("sleep_at").toInstant().toString())
            .wakeAt(rs.getTimestamp("wake_at").toInstant().toString())
            .durationMinutes(rs.getInt("duration_minutes"))
            .createdAt(rs.getTimestamp("created_at").toInstant().toString())
            .build();

    @Transactional
    public SleepEntryResponse createSleepEntry(Long userId, SleepEntryRequest req) {
        // parse ISO strings to timestamps
        try {
            OffsetDateTime sleepAt = OffsetDateTime.parse(req.getSleepAt());
            OffsetDateTime wakeAt = OffsetDateTime.parse(req.getWakeAt());
            long minutes = Math.max(0, Duration.between(sleepAt, wakeAt).toMinutes());
            if (minutes <= 0) {
                throw new BusinessException("Wake time must be after sleep time and duration should be positive");
            }

            String insertSql = "INSERT INTO sleep_entries (user_id, sleep_at, wake_at, duration_minutes) VALUES (?,?,?,?) RETURNING id, user_id, sleep_at, wake_at, duration_minutes, created_at";
            return jdbcTemplate.queryForObject(insertSql, new Object[]{userId, Timestamp.from(sleepAt.toInstant()), Timestamp.from(wakeAt.toInstant()), (int) minutes}, rowMapper);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid datetime format. Use ISO timestamps.");
        }
    }

    @Transactional(readOnly = true)
    public List<SleepEntryResponse> getUserEntries(Long userId, String fromIso, String toIso) {
        String sql = "SELECT id, user_id, sleep_at, wake_at, duration_minutes, created_at FROM sleep_entries WHERE user_id = ?";
        if (fromIso != null && toIso != null) {
            sql += " AND sleep_at >= ? AND sleep_at <= ?";
            return jdbcTemplate.query(sql + " ORDER BY sleep_at DESC", new Object[]{userId, Timestamp.from(OffsetDateTime.parse(fromIso).toInstant()), Timestamp.from(OffsetDateTime.parse(toIso).toInstant())}, rowMapper);
        }
        sql += " ORDER BY sleep_at DESC";
        return jdbcTemplate.query(sql, new Object[]{userId}, rowMapper);
    }
}
