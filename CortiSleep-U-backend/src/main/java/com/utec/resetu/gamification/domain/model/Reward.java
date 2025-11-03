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

@Table(name = "rewards")

@EntityListeners(AuditingEntityListener.class)

public class Reward {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false, length = 100)

    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)

    private String description;

    @Column(nullable = false, length = 30)

    private String rewardType;

    @Column(nullable = false)

    private Integer pointsCost;

    private Integer stock;

    @Column(length = 500)

    private String imageUrl;

    @Column(nullable = false)

    @Builder.Default

    private Boolean isActive = true;

    @Column(length = 100)

    private String partnerName;

    @Column(columnDefinition = "TEXT")

    private String termsConditions;

    private Integer expirationDays;

    @CreatedDate

    @Column(nullable = false, updatable = false)

    private LocalDateTime createdAt;

    @LastModifiedDate

    @Column(nullable = false)

    private LocalDateTime updatedAt;

}

