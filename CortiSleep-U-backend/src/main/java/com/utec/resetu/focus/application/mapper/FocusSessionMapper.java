package com.utec.resetu.focus.application.mapper;

import com.utec.resetu.focus.application.dto.FocusSessionRequest;

import com.utec.resetu.focus.application.dto.FocusSessionResponse;

import com.utec.resetu.focus.domain.model.FocusSession;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface FocusSessionMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "user", ignore = true)

    @Mapping(target = "completed", constant = "false")

    @Mapping(target = "startedAt", ignore = true)

    @Mapping(target = "endedAt", ignore = true)

    @Mapping(target = "createdAt", ignore = true)

    FocusSession toEntity(FocusSessionRequest request);

    @Mapping(target = "userId", source = "user.id")

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")

    FocusSessionResponse toDto(FocusSession session);

}

