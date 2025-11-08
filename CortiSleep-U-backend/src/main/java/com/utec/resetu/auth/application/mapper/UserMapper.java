package com.utec.resetu.auth.application.mapper;

import com.utec.resetu.auth.application.dto.RegisterRequest;
import com.utec.resetu.auth.application.dto.UserDto;
import com.utec.resetu.auth.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegisterRequest request) {
        if (request == null) return null;
        User u = new User();
        u.setEmail(request.getEmail());
        u.setFirstName(request.getFirstName());
        u.setLastName(request.getLastName());
        u.setIsActive(true);
        u.setEmailVerified(false);
        return u;
    }

    public UserDto toDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
