package com.utec.resetu.gamification.domain.model;

import jakarta.persistence.*;

import lombok.*;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

@Entity

@Table(name = "quests")

@EntityListeners(AuditingEntityListener.class)

public class Quest {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false, length = 100)

    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)

    private String description;

    @Column(nullable = false, length = 30)

    private String questType;

    @Column(nullable = false)

    private Integer targetValue;

    @Column(nullable = false)

    private Integer pointsReward;

    @Column(nullable = false, length = 20)

    private String difficulty;

    private Integer durationDays;

    @Column(nullable = false)

    @Builder.Default

    private Boolean isActive = true;

    @Column(length = 50)

    private String iconName;

    @CreatedDate

    @Column(nullable = false, updatable = false)

    private LocalDateTime createdAt;

    @LastModifiedDate

    @Column(nullable = false)

    private LocalDateTime updatedAt;

}

