package com.utec.resetu.profile.application.service;

import com.utec.resetu.profile.application.dto.ProfileRequest;
import com.utec.resetu.profile.application.dto.ProfileResponse;
import com.utec.resetu.profile.application.mapper.ProfileMapper;
import com.utec.resetu.profile.domain.model.UserProfile;
import com.utec.resetu.profile.domain.repository.UserProfileRepository;
import com.utec.resetu.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {
    
    private final UserProfileRepository userProfileRepository;
    private final ProfileMapper profileMapper;
    
    public ProfileResponse getMyProfile() {
        // TODO: Obtener userId del contexto de seguridad
        Long userId = 1L; // Placeholder
        
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado"));
        
        return profileMapper.toResponse(profile);
    }
    
    public ProfileResponse createMyProfile(ProfileRequest request) {
        // TODO: Obtener userId del contexto de seguridad
        Long userId = 1L; // Placeholder
        
        if (userProfileRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("El perfil ya existe");
        }
        
        UserProfile profile = profileMapper.toEntity(request);
        profile.setUserId(userId);
        
        UserProfile savedProfile = userProfileRepository.save(profile);
        return profileMapper.toResponse(savedProfile);
    }
    
    public ProfileResponse updateMyProfile(ProfileRequest request) {
        // TODO: Obtener userId del contexto de seguridad
        Long userId = 1L; // Placeholder
        
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado"));
        
        profileMapper.updateEntity(request, profile);
        UserProfile savedProfile = userProfileRepository.save(profile);
        
        return profileMapper.toResponse(savedProfile);
    }
}
