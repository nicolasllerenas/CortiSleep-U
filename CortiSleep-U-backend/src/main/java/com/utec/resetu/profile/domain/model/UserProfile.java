package com.utec.resetu.profile.domain.model;

import com.utec.resetu.auth.domain.model.User;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

import java.time.LocalDate;

import java.time.LocalDateTime;

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

@Entity

@Table(name = "user_profiles")

@EntityListeners(AuditingEntityListener.class)

public class UserProfile {
    
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id", nullable = false, unique = true)

    private User user;
    
    @Column(length = 50)

    private String alias;
    
    @Enumerated(EnumType.STRING)

    @Column(length = 50)

    private Faculty faculty;

    private Integer semester;
    
    @Column(length = 100)

    private String career;
    
    @Column(columnDefinition = "TEXT")

    private String bio;
    
    @Column(length = 500)

    private String avatarUrl;

    private LocalDate birthDate;

    @Column(nullable = false)

    @Builder.Default

    private Integer totalPoints = 0;

    private Integer stressLevel;

    @Column(precision = 3, scale = 1)

    @Builder.Default

    private BigDecimal sleepGoalHours = new BigDecimal("8.0");

    @Builder.Default

    private Integer screenTimeLimitMinutes = 180;

    @Column(length = 20)

    private String preferredSenseType;

    @CreatedDate

    @Column(nullable = false, updatable = false)

    private LocalDateTime createdAt;
    
    @LastModifiedDate

    @Column(nullable = false)

    private LocalDateTime updatedAt;
    
}
