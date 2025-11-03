package com.utec.resetu.focus.infrastructure.web;

import com.utec.resetu.focus.application.dto.FocusSessionRequest;

import com.utec.resetu.focus.application.dto.FocusSessionResponse;

import com.utec.resetu.focus.application.dto.FocusStatsDto;

import com.utec.resetu.focus.application.service.FocusSessionService;

import com.utec.resetu.shared.dto.ApiResponse;

import com.utec.resetu.shared.security.CurrentUserService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/focus")

@RequiredArgsConstructor

@SecurityRequirement(name = "bearerAuth")

@Tag(name = "Focus Sessions", description = "Sesiones de concentración (Pomodoro)")

public class FocusSessionController {

    private final FocusSessionService focusSessionService;

    private final CurrentUserService currentUserService;

    @PostMapping("/start")

    @Operation(summary = "Iniciar sesión de foco")

    public ResponseEntity<ApiResponse<FocusSessionResponse>> startSession(

            @Valid @RequestBody FocusSessionRequest request

    ) {

        Long userId = currentUserService.getCurrentUserId();

        FocusSessionResponse response = focusSessionService.startSession(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED)

                .body(ApiResponse.success("Sesión iniciada", response));

    }

    @PutMapping("/{id}/complete")

    @Operation(summary = "Completar sesión")

    public ResponseEntity<ApiResponse<FocusSessionResponse>> completeSession(@PathVariable Long id) {

        FocusSessionResponse response = focusSessionService.completeSession(id);

        return ResponseEntity.ok(ApiResponse.success("Sesión completada", response));

    }

    @GetMapping("/me")

    @Operation(summary = "Mis sesiones")

    public ResponseEntity<ApiResponse<Page<FocusSessionResponse>>> getMySessions(Pageable pageable) {

        Long userId = currentUserService.getCurrentUserId();

        Page<FocusSessionResponse> response = focusSessionService.getUserSessions(userId, pageable);

        return ResponseEntity.ok(ApiResponse.success("Sesiones obtenidas", response));

    }

    @GetMapping("/me/stats")

    @Operation(summary = "Estadísticas")

    public ResponseEntity<ApiResponse<FocusStatsDto>> getStats() {

        Long userId = currentUserService.getCurrentUserId();

        FocusStatsDto stats = focusSessionService.getStats(userId);

        return ResponseEntity.ok(ApiResponse.success("Estadísticas obtenidas", stats));

    }

}

