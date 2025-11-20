package com.utec.resetu.sleep.infrastructure.web;

import com.utec.resetu.shared.dto.ApiResponse;
import com.utec.resetu.sleep.application.dto.SleepEntryRequest;
import com.utec.resetu.sleep.application.dto.SleepEntryResponse;
import com.utec.resetu.sleep.application.service.SleepService;
import com.utec.resetu.shared.security.CurrentUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sleep")
@RequiredArgsConstructor
@Tag(name = "Sleep Entries", description = "Manual sleep records")
public class SleepController {

    private final SleepService sleepService;
    private final CurrentUserService currentUserService;

    // Lombok @RequiredArgsConstructor provides the constructor

    @PostMapping
    @Operation(summary = "Create sleep entry")
    public ResponseEntity<ApiResponse<SleepEntryResponse>> create(@RequestBody SleepEntryRequest request) {
        Long userId = currentUserService.getCurrentUserId();
        SleepEntryResponse res = sleepService.createSleepEntry(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Sleep entry created", res));
    }

    @GetMapping("/me")
    @Operation(summary = "Get my sleep entries")
    public ResponseEntity<ApiResponse<List<SleepEntryResponse>>> getMyEntries(@RequestParam(required = false) String from, @RequestParam(required = false) String to) {
        Long userId = currentUserService.getCurrentUserId();
        List<SleepEntryResponse> list = sleepService.getUserEntries(userId, from, to);
        return ResponseEntity.ok(ApiResponse.success("Sleep entries", list));
    }
}
