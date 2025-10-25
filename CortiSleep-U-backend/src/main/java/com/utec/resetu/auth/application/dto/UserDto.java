package com.utec.resetu.auth.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información del usuario")
public class UserDto {

    @Schema(description = "ID del usuario", example = "1")
    private Long id;

    @Schema(description = "Email del usuario", example = "estudiante@utec.edu.pe")
    private String email;

    @Schema(description = "Nombre completo", example = "Juan Pérez")
    private String fullName;

    @Schema(description = "Nombre", example = "Juan")
    private String firstName;

    @Schema(description = "Apellido", example = "Pérez")
    private String lastName;

    @Schema(description = "Estado de verificación de email", example = "false")
    private Boolean emailVerified;
}