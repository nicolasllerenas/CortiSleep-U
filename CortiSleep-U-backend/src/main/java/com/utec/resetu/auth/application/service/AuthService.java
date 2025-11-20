package com.utec.resetu.auth.application.service;

import com.utec.resetu.auth.application.dto.*;
import com.utec.resetu.auth.application.mapper.UserMapper;
import com.utec.resetu.auth.domain.model.User;
import com.utec.resetu.auth.domain.repository.UserRepository;
import com.utec.resetu.shared.exception.BusinessException;
import com.utec.resetu.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final com.utec.resetu.profile.application.service.ProfileService profileService;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registrando nuevo usuario: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("El email ya está registrado");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        user = userRepository.save(user);
        log.info("Usuario registrado exitosamente con ID: {}", user.getId());

        // Create an initial user profile automatically using the provided names
        try {
            var profileReq = com.utec.resetu.profile.application.dto.ProfileRequest.builder()
                    .alias((request.getFirstName() + " " + request.getLastName()).trim())
                    .build();
            profileService.createProfile(user.getId(), profileReq);
            log.info("Perfil inicial creado para usuario ID: {}", user.getId());
        } catch (Exception e) {
            // Non-fatal: log and continue. If profile creation fails, user is still registered.
            log.warn("No se pudo crear el perfil inicial para el usuario ID: {} — {}", user.getId(), e.getMessage());
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtExpiration / 1000)
                .user(userMapper.toDto(user))
                .build();
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.info("Intento de login para: {}", request.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!user.getIsActive()) {
            throw new BusinessException("Usuario inactivo");
        }

        userRepository.updateLastLoginTime(user.getId(), LocalDateTime.now());

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(user);

        log.info("Login exitoso para usuario: {}", user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtExpiration / 1000)
                .user(userMapper.toDto(user))
                .build();
    }

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        log.info("Solicitando refresh token");

        var refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());
        User user = refreshToken.getUser();

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String newAccessToken = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType("Bearer")
                .expiresIn(jwtExpiration / 1000)
                .user(userMapper.toDto(user))
                .build();
    }

    @Transactional
    public void logout(String userEmail) {
        log.info("Cerrando sesión para: {}", userEmail);
        
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        
        refreshTokenService.revokeAllUserTokens(user.getId());
        log.info("Sesión cerrada exitosamente");
    }
}