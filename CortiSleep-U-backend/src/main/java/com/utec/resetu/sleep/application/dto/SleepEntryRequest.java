package com.utec.resetu.sleep.application.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class SleepEntryRequest {
    // ISO timestamps expected (frontend will send full ISO strings)
    private String sleepAt;
    private String wakeAt;
}
