package com.utec.resetu.focus.application.dto;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

public class FocusSessionResponse {

    private Long id;

    private Long userId;

    private Integer durationMinutes;

    private Boolean completed;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private String sessionType;

    private String taskDescription;

    private String createdAt;

}

