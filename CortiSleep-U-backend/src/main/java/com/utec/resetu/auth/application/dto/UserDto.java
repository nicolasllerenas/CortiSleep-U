package com.utec.resetu.auth.application.dto;

import java.time.LocalDateTime;

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

    @Schema(description = "Fecha de creación del usuario", example = "2024-01-01T12:00:00Z")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de última actualización del usuario", example = "2024-01-02T12:00:00Z")
    private LocalDateTime updatedAt;
}