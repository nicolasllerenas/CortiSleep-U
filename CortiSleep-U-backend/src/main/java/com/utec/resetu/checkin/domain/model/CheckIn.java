package com.utec.resetu.checkin.domain.model;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "check_ins")
public class CheckIn {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "location_name")
    private String locationName;
    
    @Column(name = "latitude")
    private Double latitude;
    
    @Column(name = "longitude")
    private Double longitude;
    
    @Column(name = "mood_score")
    private Integer moodScore; // 1-10
    
    @Column(name = "stress_level")
    private Integer stressLevel; // 1-10
    
    @Column(name = "energy_level")
    private Integer energyLevel; // 1-10

    @Column(name = "notes")
    private String notes;
    
    @Column(name = "check_in_time", nullable = false)
    private LocalDateTime checkInTime;

    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public CheckIn() {}
    
    public CheckIn(Long id, Long userId, String locationName, Double latitude, Double longitude,
                   Integer moodScore, Integer stressLevel, Integer energyLevel, String notes,
                   LocalDateTime checkInTime, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.moodScore = moodScore;
        this.stressLevel = stressLevel;
        this.energyLevel = energyLevel;
        this.notes = notes;
        this.checkInTime = checkInTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (checkInTime == null) {
            checkInTime = LocalDateTime.now();
        }
        if (date == null) {
            // prefer deriving date from checkInTime if present
            date = (checkInTime != null) ? checkInTime.toLocalDate() : LocalDate.now();
        }
        // provide sensible defaults for optional numeric fields so inserts from
        // minimal frontend forms (which may only send date/notes) don't fail the DB
        if (this.stressLevel == null) {
            this.stressLevel = 5; // mid-scale default
        }
        if (this.moodScore == null) {
            this.moodScore = null; // keep null unless provided
        }
        if (this.energyLevel == null) {
            this.energyLevel = null;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Integer getMoodScore() { return moodScore; }
    public void setMoodScore(Integer moodScore) { this.moodScore = moodScore; }
    public Integer getStressLevel() { return stressLevel; }
    public void setStressLevel(Integer stressLevel) { this.stressLevel = stressLevel; }
    public Integer getEnergyLevel() { return energyLevel; }
    public void setEnergyLevel(Integer energyLevel) { this.energyLevel = energyLevel; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getCheckInTime() { return checkInTime; }
    public void setCheckInTime(LocalDateTime checkInTime) { this.checkInTime = checkInTime; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
