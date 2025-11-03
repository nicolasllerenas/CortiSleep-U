package com.utec.resetu.poi.application.dto;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

public class NearbyPOIRequest {

    @NotNull

    private Double latitude;

    

    @NotNull

    private Double longitude;

    

    @Min(1) @Max(50)

    private Double radiusKm = 5.0;

}

