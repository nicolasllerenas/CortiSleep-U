package com.utec.resetu.profile.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import java.time.LocalDate;





@Schema(description = "Datos para crear/actualizar perfil")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {
    

    @Size(min = 3, max = 50)

    @Schema(description = "Alias del usuario", example = "TechNinja")

    private String alias;

    @Schema(description = "Facultad", example = "CIENCIA_COMPUTACION")

    private String faculty;

    @Min(1) @Max(12)

    @Schema(description = "Semestre actual", example = "5")

    private Integer semester;

    @Size(max = 100)

    @Schema(description = "Carrera", example = "Ciencia de la Computación")

    private String career;

    @Size(max = 500)

    @Schema(description = "Biografía", example = "Estudiante apasionado por IA")

    private String bio;

    @Schema(description = "URL del avatar")

    private String avatarUrl;

    @Past

    @Schema(description = "Fecha de nacimiento", example = "2002-05-15")

    private LocalDate birthDate;

    @Min(1) @Max(10)

    @Schema(description = "Nivel de estrés (1-10)", example = "6")

    private Integer stressLevel;

    @DecimalMin("4.0") @DecimalMax("12.0")

    @Schema(description = "Horas de sueño objetivo", example = "8.0")

    private BigDecimal sleepGoalHours;

    @Min(60)

    @Schema(description = "Límite de screen time (minutos)", example = "180")

    private Integer screenTimeLimitMinutes;

    @Schema(description = "Tipo de sentido preferido", example = "AUDIO")

    private String preferredSenseType;

}
