package com.utec.resetu.focus.application.service;

import com.utec.resetu.auth.domain.model.User;
import com.utec.resetu.auth.domain.repository.UserRepository;
import com.utec.resetu.focus.application.dto.*;
import com.utec.resetu.focus.application.mapper.FocusSessionMapper;
import com.utec.resetu.focus.domain.model.FocusSession;
import com.utec.resetu.focus.domain.repository.FocusSessionRepository;
import com.utec.resetu.profile.application.service.ProfileService;
import com.utec.resetu.shared.exception.BusinessException;
import com.utec.resetu.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class FocusSessionService {

    private final FocusSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final FocusSessionMapper sessionMapper;
    private final ProfileService profileService;

    @Value("${app.points.focus-session:15}")
    private int focusSessionPoints;

    @Transactional
    public FocusSessionResponse startSession(Long userId, FocusSessionRequest request) {
        log.info("Iniciando sesión de foco para usuario ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        FocusSession session = sessionMapper.toEntity(request);
        session.setUser(user);
        session.setStartedAt(LocalDateTime.now());
        session.setCompleted(false);

        session = sessionRepository.save(session);
        log.info("Sesión de foco iniciada con ID: {}", session.getId());

        return sessionMapper.toDto(session);
    }

    @Transactional
    public FocusSessionResponse completeSession(Long sessionId) {
        log.info("Completando sesión de foco ID: {}", sessionId);

        FocusSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión no encontrada"));

        if (session.getCompleted()) {
            throw new BusinessException("La sesión ya está completada");
        }

        session.setCompleted(true);
        session.setEndedAt(LocalDateTime.now());
        session = sessionRepository.save(session);

        try {
            profileService.addPoints(session.getUser().getId(), focusSessionPoints);
            log.info("Puntos otorgados por completar sesión: {}", focusSessionPoints);
        } catch (Exception e) {
            log.warn("No se pudieron otorgar puntos: {}", e.getMessage());
        }

        return sessionMapper.toDto(session);
    }

    @Transactional(readOnly = true)
    public Page<FocusSessionResponse> getUserSessions(Long userId, Pageable pageable) {
        return sessionRepository.findByUserIdOrderByStartedAtDesc(userId, pageable)
                .map(sessionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public FocusStatsDto getStats(Long userId) {
        long totalSessions = sessionRepository.countCompletedSessions(userId);
        Long totalMinutes = sessionRepository.getTotalFocusMinutes(userId);
        long last7Days = sessionRepository.countCompletedSessionsSince(userId, LocalDateTime.now().minusDays(7));

        double completionRate = totalSessions > 0 ? 100.0 : 0.0;

        return FocusStatsDto.builder()
                .totalSessions(totalSessions)
                .completedSessions(totalSessions)
                .totalMinutes(totalMinutes != null ? totalMinutes : 0L)
                .last7DaysSessions(last7Days)
                .completionRate(completionRate)
                .build();
    }
}