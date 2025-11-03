package com.utec.resetu.senses.infrastructure.web;

import com.utec.resetu.senses.domain.model.SenseType;

import com.utec.resetu.senses.domain.model.SensoryContent;

import com.utec.resetu.senses.domain.model.UserSensoryPreference;

import com.utec.resetu.senses.domain.repository.SensoryContentRepository;

import com.utec.resetu.senses.domain.repository.UserSensoryPreferenceRepository;

import com.utec.resetu.shared.dto.ApiResponse;

import com.utec.resetu.shared.security.CurrentUserService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/senses")

@RequiredArgsConstructor

@SecurityRequirement(name = "bearerAuth")

@Tag(name = "Sensory Content", description = "Contenido multimedia para los 5 sentidos")

public class SensoryContentController {

    private final SensoryContentRepository contentRepository;

    private final UserSensoryPreferenceRepository preferenceRepository;

    private final CurrentUserService currentUserService;

    @GetMapping("/content")

    @Operation(summary = "Listar todo el contenido sensorial")

    public ResponseEntity<ApiResponse<List<SensoryContent>>> getAllContent() {

        var content = contentRepository.findByIsActiveTrueOrderByViewCountDesc();

        return ResponseEntity.ok(ApiResponse.success("Contenido disponible", content));

    }

    @GetMapping("/content/type/{type}")

    @Operation(summary = "Obtener contenido por tipo de sentido")

    public ResponseEntity<ApiResponse<List<SensoryContent>>> getContentByType(@PathVariable SenseType type) {

        var content = contentRepository.findBySenseTypeAndIsActiveTrue(type);

        return ResponseEntity.ok(ApiResponse.success("Contenido por tipo", content));

    }

    @GetMapping("/me/favorites")

    @Operation(summary = "Obtener mis favoritos")

    public ResponseEntity<ApiResponse<List<UserSensoryPreference>>> getMyFavorites() {

        Long userId = currentUserService.getCurrentUserId();

        var favorites = preferenceRepository.findByUser_IdAndFavoriteTrue(userId);

        return ResponseEntity.ok(ApiResponse.success("Mis favoritos", favorites));

    }

}

