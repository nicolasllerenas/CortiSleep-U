package com.utec.resetu.sleep.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SleepEntryResponse {
    private Long id;
    private Long userId;
    private String sleepAt;
    private String wakeAt;
    private Integer durationMinutes;
    private String createdAt;
}
