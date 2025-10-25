package com.utec.resetu.checkin.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckInRequest {
    
    @Size(max = 200, message = "El nombre de la ubicación no puede exceder 200 caracteres")
    private String locationName;
    
    private Double latitude;
    
    private Double longitude;
    
    @Min(value = 1, message = "El puntaje de ánimo debe ser entre 1 y 10")
    @Max(value = 10, message = "El puntaje de ánimo debe ser entre 1 y 10")
    private Integer moodScore;
    
    @Min(value = 1, message = "El nivel de estrés debe ser entre 1 y 10")
    @Max(value = 10, message = "El nivel de estrés debe ser entre 1 y 10")
    private Integer stressLevel;
    
    @Min(value = 1, message = "El nivel de energía debe ser entre 1 y 10")
    @Max(value = 10, message = "El nivel de energía debe ser entre 1 y 10")
    private Integer energyLevel;
    
    @Size(max = 500, message = "Las notas no pueden exceder 500 caracteres")
    private String notes;
}
