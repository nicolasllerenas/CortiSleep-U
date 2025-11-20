package com.utec.resetu.gamification.infrastructure.web;

import com.utec.resetu.gamification.domain.model.Quest;

import com.utec.resetu.gamification.domain.model.UserQuest;

import com.utec.resetu.gamification.domain.repository.QuestRepository;

import com.utec.resetu.gamification.domain.repository.UserQuestRepository;

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

@RequestMapping({"/quests","/gamification/quests"})

@RequiredArgsConstructor

@SecurityRequirement(name = "bearerAuth")

@Tag(name = "Quests", description = "Misiones gamificadas")

public class QuestController {

    private final QuestRepository questRepository;

    private final UserQuestRepository userQuestRepository;

    private final CurrentUserService currentUserService;

    @GetMapping

    @Operation(summary = "Listar todas las quests disponibles")

    public ResponseEntity<ApiResponse<List<Quest>>> getAllQuests() {

        var quests = questRepository.findByIsActiveTrueOrderByDifficultyAsc();

        return ResponseEntity.ok(ApiResponse.success("Quests disponibles", quests));

    }

    @GetMapping("/me")

    @Operation(summary = "Obtener mis quests")

    public ResponseEntity<ApiResponse<List<UserQuest>>> getMyQuests() {

        Long userId = currentUserService.getCurrentUserId();

        var quests = userQuestRepository.findByUser_IdAndStatus(userId, "IN_PROGRESS");

        return ResponseEntity.ok(ApiResponse.success("Mis quests", quests));

    }

}

