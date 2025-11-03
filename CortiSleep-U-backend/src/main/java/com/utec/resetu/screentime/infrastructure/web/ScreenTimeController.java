package com.utec.resetu.screentime.infrastructure.web;

import com.utec.resetu.screentime.domain.model.ScreenTimeEntry;

import com.utec.resetu.screentime.domain.repository.ScreenTimeRepository;

import com.utec.resetu.shared.dto.ApiResponse;

import com.utec.resetu.shared.security.CurrentUserService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;

@RestController

@RequestMapping("/screentime")

@RequiredArgsConstructor

@SecurityRequirement(name = "bearerAuth")

@Tag(name = "Screen Time", description = "Monitoreo de tiempo en pantalla")

public class ScreenTimeController {

    private final ScreenTimeRepository screenTimeRepository;

    private final CurrentUserService currentUserService;

    @GetMapping("/me")

    @Operation(summary = "Obtener mi screen time")

    public ResponseEntity<ApiResponse<List<ScreenTimeEntry>>> getMyScreenTime() {

        Long userId = currentUserService.getCurrentUserId();

        LocalDate end = LocalDate.now();

        LocalDate start = end.minusDays(30);

        var entries = screenTimeRepository.findByUser_IdAndDateBetweenOrderByDateDesc(userId, start, end);

        return ResponseEntity.ok(ApiResponse.success("Screen time obtenido", entries));

    }

}

