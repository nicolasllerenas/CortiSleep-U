package com.utec.resetu.auth.application.service;

import com.utec.resetu.auth.domain.model.RefreshToken;
import com.utec.resetu.auth.domain.model.User;
import com.utec.resetu.auth.domain.repository.RefreshTokenRepository;
import com.utec.resetu.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    @Transactional
    public String createRefreshToken(User user) {
        String token = UUID.randomUUID().toString();
        
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiresAt(LocalDateTime.now().plusSeconds(refreshExpiration / 1000))
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);
        log.debug("Refresh token creado para usuario: {}", user.getEmail());
        
        return token;
    }

    @Transactional(readOnly = true)
    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BusinessException("Refresh token inválido"));

        if (refreshToken.getRevoked()) {
            throw new BusinessException("Refresh token revocado");
        }

        if (refreshToken.isExpired()) {
            throw new BusinessException("Refresh token expirado");
        }

        return refreshToken;
    }

    @Transactional
    public void revokeAllUserTokens(Long userId) {
        refreshTokenRepository.revokeAllByUserId(userId);
        log.info("Todos los tokens del usuario {} han sido revocados", userId);
    }

    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void cleanupExpiredTokens() {
        log.info("Iniciando limpieza de tokens expirados");
        refreshTokenRepository.deleteExpiredTokens(LocalDateTime.now());
        log.info("Limpieza de tokens completada");
    }
}