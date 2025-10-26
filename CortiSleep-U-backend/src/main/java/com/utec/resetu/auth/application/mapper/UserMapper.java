
package com.utec.resetu.auth.application.mapper;

import com.utec.resetu.auth.application.dto.RegisterRequest;
import com.utec.resetu.auth.application.dto.UserDto;
import com.utec.resetu.auth.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "emailVerified", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    User toEntity(RegisterRequest request);

    @Mapping(target = "fullName", expression = "java(user.getFullName())")
    UserDto toDto(User user);
}