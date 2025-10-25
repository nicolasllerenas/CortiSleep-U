package com.utec.resetu.checkin.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckInStatsDto {
    
    private Long userId;
    private Long totalCheckIns;
    private Double averageMoodScore;
    private Double averageStressLevel;
    private Double averageEnergyLevel;
    private Long checkInsToday;
    private Long checkInsThisWeek;
    private Long checkInsThisMonth;
    private List<CheckInResponse> recentCheckIns;
    private LocalDateTime lastCheckIn;
}
