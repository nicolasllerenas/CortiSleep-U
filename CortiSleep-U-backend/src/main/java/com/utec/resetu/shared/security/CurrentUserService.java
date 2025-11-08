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

@Service
public class CurrentUserService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CurrentUserService.class);
    public CurrentUserService(UserRepository userRepository) { this.userRepository = userRepository; }

    private final UserRepository userRepository;

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

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }

    public boolean isCurrentUser(Long userId) {
        return getCurrentUserId().equals(userId);
    }

    public void validateCurrentUser(Long userId) {
        if (!isCurrentUser(userId)) {
            throw new UnauthorizedException("No tienes permisos para acceder a este recurso");
        }
    }
}