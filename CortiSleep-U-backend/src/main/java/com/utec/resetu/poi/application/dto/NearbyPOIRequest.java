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
    
    @NotNull(message = "La latitud es obligatoria")
    private Double latitude;
    
    @NotNull(message = "La longitud es obligatoria")
    private Double longitude;
    
    @Min(1) 
    @Max(50)
    @Builder.Default  // ← AGREGAR ESTO
    private Double radiusKm = 5.0;
}