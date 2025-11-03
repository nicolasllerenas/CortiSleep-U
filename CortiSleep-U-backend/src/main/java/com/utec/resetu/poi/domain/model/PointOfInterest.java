package com.utec.resetu.poi.domain.model;

import jakarta.persistence.*;

import lombok.*;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

import java.time.LocalDateTime;

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

@Entity

@Table(name = "points_of_interest")

@EntityListeners(AuditingEntityListener.class)

public class PointOfInterest {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false, length = 100)

    private String name;

    @Column(columnDefinition = "TEXT")

    private String description;

    @Enumerated(EnumType.STRING)

    @Column(nullable = false, length = 30)

    private POICategory category;

    @Column(nullable = false, precision = 10, scale = 8)

    private BigDecimal latitude;

    @Column(nullable = false, precision = 11, scale = 8)

    private BigDecimal longitude;

    @Column(length = 200)

    private String address;

    @Column(length = 500)

    private String imageUrl;

    @Column(columnDefinition = "TEXT")

    private String benefits;

    @Column(length = 100)

    private String openingHours;

    @Column(length = 100)

    private String contactInfo;

    @Column(length = 50)

    private String university;

    @Column(nullable = false)

    @Builder.Default

    private Boolean isActive = true;

    @Column(nullable = false)

    @Builder.Default

    private Integer pointsReward = 10;

    @Column(nullable = false)

    @Builder.Default

    private Boolean verified = false;

    @Column(precision = 2, scale = 1)

    @Builder.Default

    private BigDecimal averageRating = BigDecimal.ZERO;

    @Column(nullable = false)

    @Builder.Default

    private Integer totalRatings = 0;

    @CreatedDate

    @Column(nullable = false, updatable = false)

    private LocalDateTime createdAt;

    @LastModifiedDate

    @Column(nullable = false)

    private LocalDateTime updatedAt;

}

