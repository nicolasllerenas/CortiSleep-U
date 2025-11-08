package com.utec.resetu.focus.application.dto;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

import lombok.Data;

@Data
public class FocusStatsDto {
    public FocusStatsDto() {}
    public FocusStatsDto(Long totalSessions, Long completedSessions, Long totalMinutes, Long last7DaysSessions, Double completionRate) {
        this.totalSessions=totalSessions; this.completedSessions=completedSessions; this.totalMinutes=totalMinutes; this.last7DaysSessions=last7DaysSessions; this.completionRate=completionRate;
    }

    private Long totalSessions;

    private Long completedSessions;

    private Long totalMinutes;

    private Long last7DaysSessions;

    private Double completionRate;

}

