package com.utec.resetu.checkin.infrastructure.web;

import com.utec.resetu.checkin.application.dto.CheckInRequest;
import com.utec.resetu.checkin.application.dto.CheckInResponse;
import com.utec.resetu.checkin.application.dto.CheckInStatsDto;
import com.utec.resetu.checkin.application.service.CheckInService;
import com.utec.resetu.shared.dto.ApiResponse;
import com.utec.resetu.shared.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/checkin")
@RequiredArgsConstructor
@Tag(name = "CheckIn", description = "Gestión de check-ins de usuario")
public class CheckInController {
    
    private final CheckInService checkInService;
    
    @PostMapping
    @Operation(summary = "Realizar un nuevo check-in")
    public ResponseEntity<ApiResponse<CheckInResponse>> createCheckIn(
            @Valid @RequestBody CheckInRequest request) {
        CheckInResponse checkIn = checkInService.createCheckIn(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(checkIn));
    }
    
    @GetMapping("/my")
    @Operation(summary = "Obtener mis check-ins")
    public ResponseEntity<ApiResponse<PageResponse<CheckInResponse>>> getMyCheckIns(
            Pageable pageable) {
        PageResponse<CheckInResponse> checkIns = checkInService.getMyCheckIns(pageable);
        return ResponseEntity.ok(ApiResponse.success(checkIns));
    }
    
    @GetMapping("/stats")
    @Operation(summary = "Obtener estadísticas de check-ins")
    public ResponseEntity<ApiResponse<CheckInStatsDto>> getCheckInStats() {
        CheckInStatsDto stats = checkInService.getCheckInStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
    
    @GetMapping("/today")
    @Operation(summary = "Obtener check-ins de hoy")
    public ResponseEntity<ApiResponse<List<CheckInResponse>>> getTodayCheckIns() {
        List<CheckInResponse> checkIns = checkInService.getTodayCheckIns();
        return ResponseEntity.ok(ApiResponse.success(checkIns));
    }
}
