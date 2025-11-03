package com.utec.resetu.poi.infrastructure.web;

import com.utec.resetu.poi.application.dto.*;

import com.utec.resetu.poi.application.service.POIService;

import com.utec.resetu.poi.domain.model.UserPOIVisit;

import com.utec.resetu.shared.dto.ApiResponse;

import com.utec.resetu.shared.security.CurrentUserService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/poi")

@RequiredArgsConstructor

@SecurityRequirement(name = "bearerAuth")

@Tag(name = "Points of Interest", description = "Lugares anti-estrés")

public class POIController {

    private final POIService poiService;

    private final CurrentUserService currentUserService;

    @GetMapping

    @Operation(summary = "Listar todos los POI")

    public ResponseEntity<ApiResponse<List<POIResponse>>> getAllPOIs() {

        List<POIResponse> pois = poiService.getAllPOIs();

        return ResponseEntity.ok(ApiResponse.success("POIs obtenidos", pois));

    }

    @GetMapping("/{id}")

    @Operation(summary = "Obtener POI por ID")

    public ResponseEntity<ApiResponse<POIResponse>> getPOI(@PathVariable Long id) {

        POIResponse poi = poiService.getPOIById(id);

        return ResponseEntity.ok(ApiResponse.success("POI obtenido", poi));

    }

    @PostMapping("/nearby")

    @Operation(summary = "POI cercanos")

    public ResponseEntity<ApiResponse<List<POIResponse>>> getNearbyPOIs(

            @Valid @RequestBody NearbyPOIRequest request

    ) {

        List<POIResponse> pois = poiService.getNearbyPOIs(request);

        return ResponseEntity.ok(ApiResponse.success("POIs cercanos obtenidos", pois));

    }

    @GetMapping("/search")

    @Operation(summary = "Buscar POI")

    public ResponseEntity<ApiResponse<List<POIResponse>>> searchPOIs(@RequestParam String q) {

        List<POIResponse> pois = poiService.searchPOIs(q);

        return ResponseEntity.ok(ApiResponse.success("Resultados de búsqueda", pois));

    }

    @PostMapping("/{id}/visit")

    @Operation(summary = "Registrar visita a POI")

    public ResponseEntity<ApiResponse<Void>> visitPOI(

            @PathVariable Long id,

            @Valid @RequestBody VisitPOIRequest request

    ) {

        Long userId = currentUserService.getCurrentUserId();

        poiService.visitPOI(userId, id, request);

        return ResponseEntity.ok(ApiResponse.success("Visita registrada", null));

    }

    @GetMapping("/me/visits")

    @Operation(summary = "Mis visitas")

    public ResponseEntity<ApiResponse<Page<UserPOIVisit>>> getMyVisits(Pageable pageable) {

        Long userId = currentUserService.getCurrentUserId();

        var visits = poiService.getMyVisits(userId, pageable);

        return ResponseEntity.ok(ApiResponse.success("Visitas obtenidas", visits));

    }

}

