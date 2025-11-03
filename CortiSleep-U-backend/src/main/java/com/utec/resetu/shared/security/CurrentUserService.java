package com.utec.resetu.shared.security;

import com.utec.resetu.auth.domain.model.User;
import com.utec.resetu.auth.domain.repository.UserRepository;
import com.utec.resetu.shared.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    /**
     * Obtiene el usuario autenticado actual desde el contexto de seguridad
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Usuario no autenticado");
        }

        Object principal = authentication.getPrincipal();
        
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new UnauthorizedException("Usuario no encontrado"));
        }
        
        if (principal instanceof String) {
            return userRepository.findByEmail((String) principal)
                    .orElseThrow(() -> new UnauthorizedException("Usuario no encontrado"));
        }

        throw new UnauthorizedException("Principal de tipo no soportado");
    }

    /**
     * Obtiene el ID del usuario autenticado actual
     */
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    /**
     * Obtiene el email del usuario autenticado actual
     */
    public String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }

    /**
     * Verifica si el usuario actual es el propietario del recurso
     */
    public boolean isCurrentUser(Long userId) {
        return getCurrentUserId().equals(userId);
    }

    /**
     * Valida que el usuario actual sea el propietario, lanza excepción si no lo es
     */
    public void validateCurrentUser(Long userId) {
        if (!isCurrentUser(userId)) {
            throw new UnauthorizedException("No tienes permisos para acceder a este recurso");
        }
    }
}