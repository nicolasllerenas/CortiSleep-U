package com.utec.resetu.focus.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;





@Data
public class FocusSessionRequest {
    public FocusSessionRequest() {}
    public FocusSessionRequest(Integer durationMinutes, String sessionType, String taskDescription) {
        this.durationMinutes = durationMinutes; this.sessionType = sessionType; this.taskDescription = taskDescription;
    }

    @NotNull

    @Min(5) @Max(120)

    @Schema(description = "Duración en minutos", example = "25")

    private Integer durationMinutes;

    @Schema(description = "Tipo de sesión", example = "POMODORO")

    private String sessionType;

    @Size(max = 200)

    @Schema(description = "Descripción de la tarea")

    private String taskDescription;

}

