package com.utec.resetu.focus.application.dto;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

public class FocusStatsDto {

    private Long totalSessions;

    private Long completedSessions;

    private Long totalMinutes;

    private Long last7DaysSessions;

    private Double completionRate;

}

