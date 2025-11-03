package com.utec.resetu.profile.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import java.time.LocalDate;

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

@Schema(description = "Perfil completo del usuario")

public class ProfileResponse {

    private Long id;

    private Long userId;

    private String userEmail;

    private String fullName;

    private String alias;

    private String faculty;

    private Integer semester;

    private String career;

    private String bio;

    private String avatarUrl;

    private LocalDate birthDate;

    private Integer age;

    private Integer totalPoints;

    private Integer stressLevel;

    private BigDecimal sleepGoalHours;

    private Integer screenTimeLimitMinutes;

    private String preferredSenseType;

    private String createdAt;

}
