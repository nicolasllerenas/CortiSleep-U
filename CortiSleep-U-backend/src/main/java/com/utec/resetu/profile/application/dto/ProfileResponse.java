package com.utec.resetu.profile.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import java.time.LocalDate;





@Schema(description = "Perfil completo del usuario")

@Data
public class ProfileResponse {
    public ProfileResponse() {}
    public ProfileResponse(Long id, Long userId, String userEmail, String fullName, String alias, String faculty, Integer semester, String career, String bio, String avatarUrl, java.time.LocalDate birthDate, Integer age, Integer totalPoints, Integer stressLevel, java.math.BigDecimal sleepGoalHours, Integer screenTimeLimitMinutes, String preferredSenseType, String createdAt) {
        this.id=id; this.userId=userId; this.userEmail=userEmail; this.fullName=fullName; this.alias=alias; this.faculty=faculty; this.semester=semester; this.career=career; this.bio=bio; this.avatarUrl=avatarUrl; this.birthDate=birthDate; this.age=age; this.totalPoints=totalPoints; this.stressLevel=stressLevel; this.sleepGoalHours=sleepGoalHours; this.screenTimeLimitMinutes=screenTimeLimitMinutes; this.preferredSenseType=preferredSenseType; this.createdAt=createdAt;
    }

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
