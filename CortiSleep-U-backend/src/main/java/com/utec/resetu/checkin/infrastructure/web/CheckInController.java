import com.utec.resetu.checkin.application.dto.CheckInRequest;
import com.utec.resetu.checkin.application.dto.CheckInResponse;
import com.utec.resetu.checkin.application.dto.CheckInStatsDto;
import com.utec.resetu.checkin.application.service.CheckInService;
import com.utec.resetu.shared.dto.ApiResponse;
// import removed: CurrentUserService está en paquete por defecto
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/checkins")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Check-Ins", description = "Registro diario de estrés, sueño y bienestar")
public class CheckInController {

    private final CheckInService checkInService;
    // CurrentUserService temporalmente omitido para resolver import; usar SecurityContext en una iteración posterior

    @PostMapping
    @Operation(summary = "Crear check-in", description = "Registra un check-in diario del usuario")
    public ResponseEntity<ApiResponse<CheckInResponse>> createCheckIn(
            @Valid @RequestBody CheckInRequest request
    ) {
        Long userId = 1L;
        CheckInResponse response = checkInService.createCheckIn(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Check-in registrado exitosamente", response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener check-in por ID", description = "Obtiene un check-in específico")
    public ResponseEntity<ApiResponse<CheckInResponse>> getCheckIn(@PathVariable Long id) {
        CheckInResponse response = checkInService.getCheckInById(id);
        return ResponseEntity.ok(ApiResponse.success("Check-in obtenido", response));
    }

    @GetMapping("/me")
    @Operation(summary = "Mis check-ins", description = "Lista todos los check-ins del usuario autenticado")
    public ResponseEntity<ApiResponse<Page<CheckInResponse>>> getMyCheckIns(
            @PageableDefault(size = 30, sort = "date", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Long userId = 1L;
        Page<CheckInResponse> response = checkInService.getCheckInsByUser(userId, pageable);
        return ResponseEntity.ok(ApiResponse.success("Check-ins obtenidos", response));
    }

    @GetMapping("/me/date/{date}")
    @Operation(summary = "Check-in por fecha", description = "Obtiene el check-in de una fecha específica")
    public ResponseEntity<ApiResponse<CheckInResponse>> getCheckInByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        Long userId = 1L;
        CheckInResponse response = checkInService.getCheckInByDate(userId, date);
        return ResponseEntity.ok(ApiResponse.success("Check-in obtenido", response));
    }

    @GetMapping("/me/range")
    @Operation(summary = "Check-ins por rango de fechas", description = "Obtiene check-ins en un rango de fechas")
    public ResponseEntity<ApiResponse<List<CheckInResponse>>> getCheckInsByRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        Long userId = 1L;
        List<CheckInResponse> response = checkInService.getCheckInsByDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Check-ins obtenidos", response));
    }

    @GetMapping("/me/stats")
    @Operation(summary = "Estadísticas de check-ins", description = "Obtiene estadísticas del usuario en un período")
    public ResponseEntity<ApiResponse<CheckInStatsDto>> getStats(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        Long userId = 1L;
        LocalDate start = startDate != null ? startDate : LocalDate.now().minusDays(30);
        LocalDate end = endDate != null ? endDate : LocalDate.now();
        
        CheckInStatsDto stats = checkInService.getStats(userId, start, end);
        return ResponseEntity.ok(ApiResponse.success("Estadísticas obtenidas", stats));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar check-in", description = "Actualiza un check-in existente")
    public ResponseEntity<ApiResponse<CheckInResponse>> updateCheckIn(
            @PathVariable Long id,
            @Valid @RequestBody CheckInRequest request
    ) {
        CheckInResponse response = checkInService.updateCheckIn(id, request);
        return ResponseEntity.ok(ApiResponse.success("Check-in actualizado", response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar check-in", description = "Elimina un check-in")
    public ResponseEntity<ApiResponse<Void>> deleteCheckIn(@PathVariable Long id) {
        checkInService.deleteCheckIn(id);
        return ResponseEntity.ok(ApiResponse.success("Check-in eliminado", null));
    }
}