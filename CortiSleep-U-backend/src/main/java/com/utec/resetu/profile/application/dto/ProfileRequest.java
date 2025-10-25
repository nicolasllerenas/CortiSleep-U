package com.utec.resetu.profile.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {
    
    @NotBlank(message = "El nombre es requerido")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String firstName;
    
    @NotBlank(message = "El apellido es requerido")
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    private String lastName;
    
    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email es requerido")
    private String email;
    
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String phone;
    
    private Long facultyId;
    
    @Size(max = 20, message = "El código de estudiante no puede exceder 20 caracteres")
    private String studentCode;
    
    @Size(max = 500, message = "La biografía no puede exceder 500 caracteres")
    private String bio;
}
