package com.utec.resetu.checkin.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CheckInResponse {
    public CheckInResponse() {}
    public CheckInResponse(Long id, Long userId, String locationName, Double latitude, Double longitude, Integer moodScore, Integer stressLevel, Integer energyLevel, String notes, java.time.LocalDate date, java.time.LocalDateTime checkInTime, java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt) {
        this.id = id; this.userId = userId; this.locationName = locationName; this.latitude = latitude; this.longitude = longitude; this.moodScore = moodScore; this.stressLevel = stressLevel; this.energyLevel = energyLevel; this.notes = notes; this.date = date; this.checkInTime = checkInTime; this.createdAt = createdAt; this.updatedAt = updatedAt;
    }
    
    private Long id;
    private Long userId;
    private String locationName;
    private Double latitude;
    private Double longitude;
    private Integer moodScore;
    private Integer stressLevel;
    private Integer energyLevel;
    private String notes;
    private LocalDate date;
    private LocalDateTime checkInTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
