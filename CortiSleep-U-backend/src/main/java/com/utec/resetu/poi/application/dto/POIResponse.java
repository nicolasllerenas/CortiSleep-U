package com.utec.resetu.poi.application.dto;

import jakarta.validation.constraints.*;

import lombok.*;

import java.math.BigDecimal;

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

public class POIResponse {

    private Long id;

    private String name;

    private String description;

    private String category;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String address;

    private String imageUrl;

    private String benefits;

    private String openingHours;

    private String university;

    private Integer pointsReward;

    private BigDecimal averageRating;

    private Integer totalRatings;

    private Double distanceKm;

}

