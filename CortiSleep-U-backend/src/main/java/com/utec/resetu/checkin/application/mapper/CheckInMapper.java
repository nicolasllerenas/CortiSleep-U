package com.utec.resetu.checkin.application.mapper;

import com.utec.resetu.checkin.application.dto.CheckInRequest;
import com.utec.resetu.checkin.application.dto.CheckInResponse;
import com.utec.resetu.checkin.domain.model.CheckIn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CheckInMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    CheckIn toEntity(CheckInRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CheckInResponse toDto(CheckIn checkIn);
}