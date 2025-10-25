package com.utec.resetu.checkin.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckInResponse {
    
    private Long id;
    private Long userId;
    private String locationName;
    private Double latitude;
    private Double longitude;
    private Integer moodScore;
    private Integer stressLevel;
    private Integer energyLevel;
    private String notes;
    private LocalDateTime checkInTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
