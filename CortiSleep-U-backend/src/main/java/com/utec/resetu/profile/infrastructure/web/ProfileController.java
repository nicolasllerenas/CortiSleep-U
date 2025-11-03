package com.utec.resetu.profile.infrastructure.web;

import com.utec.resetu.profile.application.dto.ProfileRequest;

import com.utec.resetu.profile.application.dto.ProfileResponse;

import com.utec.resetu.profile.application.service.ProfileService;

import com.utec.resetu.shared.dto.ApiResponse;

import com.utec.resetu.shared.security.CurrentUserService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/profiles")

@RequiredArgsConstructor

@SecurityRequirement(name = "bearerAuth")

@Tag(name = "Profiles", description = "Gestión de perfiles de usuario")

public class ProfileController {

    private final ProfileService profileService;

    private final CurrentUserService currentUserService;

    @PostMapping

    @Operation(summary = "Crear perfil")

    public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(

            @Valid @RequestBody ProfileRequest request

    ) {

        Long userId = currentUserService.getCurrentUserId();

        ProfileResponse response = profileService.createProfile(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED)

                .body(ApiResponse.success("Perfil creado exitosamente", response));

    }

    @GetMapping("/me")

    @Operation(summary = "Obtener mi perfil")

    public ResponseEntity<ApiResponse<ProfileResponse>> getMyProfile() {

        Long userId = currentUserService.getCurrentUserId();

        ProfileResponse response = profileService.getProfileByUserId(userId);

        return ResponseEntity.ok(ApiResponse.success("Perfil obtenido", response));

    }

    @PutMapping("/me")

    @Operation(summary = "Actualizar mi perfil")

    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(

            @Valid @RequestBody ProfileRequest request

    ) {

        Long userId = currentUserService.getCurrentUserId();

        ProfileResponse response = profileService.updateProfile(userId, request);

        return ResponseEntity.ok(ApiResponse.success("Perfil actualizado", response));

    }

    @DeleteMapping("/me")

    @Operation(summary = "Eliminar mi perfil")

    public ResponseEntity<ApiResponse<Void>> deleteProfile() {

        Long userId = currentUserService.getCurrentUserId();

        profileService.deleteProfile(userId);

        return ResponseEntity.ok(ApiResponse.success("Perfil eliminado", null));

    }

    @GetMapping("/me/points")

    @Operation(summary = "Obtener mis puntos")

    public ResponseEntity<ApiResponse<Integer>> getMyPoints() {

        Long userId = currentUserService.getCurrentUserId();

        Integer points = profileService.getTotalPoints(userId);

        return ResponseEntity.ok(ApiResponse.success("Puntos obtenidos", points));

    }

}
