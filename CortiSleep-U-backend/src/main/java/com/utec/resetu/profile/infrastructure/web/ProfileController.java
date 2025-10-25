package com.utec.resetu.profile.infrastructure.web;

import com.utec.resetu.profile.application.dto.ProfileRequest;
import com.utec.resetu.profile.application.dto.ProfileResponse;
import com.utec.resetu.profile.application.service.ProfileService;
import com.utec.resetu.shared.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "Gestión de perfiles de usuario")
public class ProfileController {
    
    private final ProfileService profileService;
    
    @GetMapping("/me")
    @Operation(summary = "Obtener perfil del usuario autenticado")
    public ResponseEntity<ApiResponse<ProfileResponse>> getMyProfile() {
        ProfileResponse profile = profileService.getMyProfile();
        return ResponseEntity.ok(ApiResponse.success(profile));
    }
    
    @PutMapping("/me")
    @Operation(summary = "Actualizar perfil del usuario autenticado")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateMyProfile(
            @Valid @RequestBody ProfileRequest request) {
        ProfileResponse profile = profileService.updateMyProfile(request);
        return ResponseEntity.ok(ApiResponse.success(profile));
    }
    
    @PostMapping("/me")
    @Operation(summary = "Crear perfil del usuario autenticado")
    public ResponseEntity<ApiResponse<ProfileResponse>> createMyProfile(
            @Valid @RequestBody ProfileRequest request) {
        ProfileResponse profile = profileService.createMyProfile(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(profile));
    }
}
