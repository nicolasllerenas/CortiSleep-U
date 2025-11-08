package com.utec.resetu.focus.application.dto;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;





@Data
public class FocusSessionResponse {
    public FocusSessionResponse() {}
    public FocusSessionResponse(Long id, Long userId, Integer durationMinutes, Boolean completed, java.time.LocalDateTime startedAt, java.time.LocalDateTime endedAt, String sessionType, String taskDescription, String createdAt) {
        this.id=id; this.userId=userId; this.durationMinutes=durationMinutes; this.completed=completed; this.startedAt=startedAt; this.endedAt=endedAt; this.sessionType=sessionType; this.taskDescription=taskDescription; this.createdAt=createdAt;
    }

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

