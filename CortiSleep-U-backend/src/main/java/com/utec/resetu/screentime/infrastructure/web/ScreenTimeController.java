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
   private final com.utec.resetu.screentime.application.service.ScreenTimeService screenTimeService;

    @GetMapping("/me")

    @Operation(summary = "Obtener mi screen time")

    public ResponseEntity<ApiResponse<List<ScreenTimeEntry>>> getMyScreenTime() {

        Long userId = currentUserService.getCurrentUserId();

        LocalDate end = LocalDate.now();

        LocalDate start = end.minusDays(30);

        var entries = screenTimeRepository.findByUser_IdAndDateBetweenOrderByDateDesc(userId, start, end);

        return ResponseEntity.ok(ApiResponse.success("Screen time obtenido", entries));

    }

   @PostMapping("/entries")
   @Operation(summary = "Registrar/actualizar screen time del día")
   public ResponseEntity<ApiResponse<ScreenTimeEntry>> upsertEntry(
           @RequestParam String date, // yyyy-MM-dd
           @RequestParam Integer totalMinutes,
           @RequestParam(required = false) String deviceType
   ) {
       var entry = screenTimeService.upsertEntry(java.time.LocalDate.parse(date), totalMinutes, deviceType);
       return ResponseEntity.ok(ApiResponse.success("Entry actualizada", entry));
   }

   @PostMapping("/apps")
   @Operation(summary = "Registrar uso de una app en una entry")
   public ResponseEntity<ApiResponse<com.utec.resetu.screentime.domain.model.AppUsage>> addAppUsage(
           @RequestParam Long entryId,
           @RequestParam String appName,
           @RequestParam(required = false) String appCategory,
           @RequestParam Integer usageMinutes
   ) {
       var usage = screenTimeService.addAppUsage(entryId, appName, appCategory, usageMinutes);
       return ResponseEntity.ok(ApiResponse.success("Uso registrado", usage));
   }

   @GetMapping("/stats")
   @Operation(summary = "Estadísticas de screen time")
   public ResponseEntity<ApiResponse<java.util.Map<String, Object>>> getStats(
           @RequestParam(required = false, defaultValue = "weekly") String range
   ) {
       var stats = screenTimeService.getStats(range);
       return ResponseEntity.ok(ApiResponse.success("Estadísticas", stats));
   }
}

