package com.utec.resetu.profile.application.service;

import com.utec.resetu.auth.domain.model.User;
import com.utec.resetu.auth.domain.repository.UserRepository;
import com.utec.resetu.profile.application.dto.ProfileRequest;
import com.utec.resetu.profile.application.dto.ProfileResponse;
import com.utec.resetu.profile.application.mapper.ProfileMapper;
import com.utec.resetu.profile.domain.model.UserProfile;
import com.utec.resetu.profile.domain.repository.UserProfileRepository;
import com.utec.resetu.shared.exception.BusinessException;
import com.utec.resetu.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;

    @Transactional
    public ProfileResponse createProfile(Long userId, ProfileRequest request) {
        log.info("Creando perfil para usuario ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (profileRepository.existsByUserId(userId)) {
            throw new BusinessException("El usuario ya tiene un perfil creado");
        }

        UserProfile profile = profileMapper.toEntity(request);
        profile.setUser(user);
        
        profile = profileRepository.save(profile);
        log.info("Perfil creado exitosamente con ID: {}", profile.getId());

        return profileMapper.toDto(profile);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "profiles", key = "#userId")
    public ProfileResponse getProfileByUserId(Long userId) {
        log.debug("Obteniendo perfil para usuario ID: {}", userId);

        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado"));

        return profileMapper.toDto(profile);
    }

    @Transactional
    @CacheEvict(value = "profiles", key = "#userId")
    public ProfileResponse updateProfile(Long userId, ProfileRequest request) {
        log.info("Actualizando perfil para usuario ID: {}", userId);

        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado"));

        profileMapper.updateEntityFromDto(request, profile);
        profile = profileRepository.save(profile);

        return profileMapper.toDto(profile);
    }

    @Transactional
    @CacheEvict(value = "profiles", key = "#userId")
    public void deleteProfile(Long userId) {
        log.info("Eliminando perfil para usuario ID: {}", userId);

        UserProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado"));

        profileRepository.delete(profile);
    }

    @Transactional
    public void addPoints(Long userId, Integer points) {
        log.info("Añadiendo {} puntos al usuario ID: {}", points, userId);
        
        if (points <= 0) {
            throw new BusinessException("Los puntos deben ser mayores a cero");
        }

        profileRepository.addPoints(userId, points);
    }

    @Transactional
    public void deductPoints(Long userId, Integer points) {
        log.info("Deduciendo {} puntos al usuario ID: {}", points, userId);
        
        if (points <= 0) {
            throw new BusinessException("Los puntos deben ser mayores a cero");
        }

        int updated = profileRepository.deductPoints(userId, points);
        if (updated == 0) {
            throw new BusinessException("Puntos insuficientes");
        }
    }

    @Transactional(readOnly = true)
    public Integer getTotalPoints(Long userId) {
        return profileRepository.findTotalPointsByUserId(userId).orElse(0);
    }
}
