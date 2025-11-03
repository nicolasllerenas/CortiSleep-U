package com.utec.resetu.gamification.infrastructure.web;

import com.utec.resetu.gamification.domain.model.Reward;

import com.utec.resetu.gamification.domain.repository.RewardRepository;

import com.utec.resetu.shared.dto.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/rewards")

@RequiredArgsConstructor

@SecurityRequirement(name = "bearerAuth")

@Tag(name = "Rewards", description = "Premios canjeables")

public class RewardController {

    private final RewardRepository rewardRepository;

    @GetMapping

    @Operation(summary = "Listar todos los rewards disponibles")

    public ResponseEntity<ApiResponse<List<Reward>>> getAllRewards() {

        var rewards = rewardRepository.findByIsActiveTrueOrderByPointsCostAsc();

        return ResponseEntity.ok(ApiResponse.success("Rewards disponibles", rewards));

    }

}

