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

public class VisitPOIRequest {

    private Integer durationMinutes;

    

    @Min(1) @Max(5)

    private Integer rating;

    

    @Size(max = 500)

    private String comment;

}

