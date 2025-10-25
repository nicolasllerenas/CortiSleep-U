package com.utec.resetu.checkin.application.mapper;

import com.utec.resetu.checkin.application.dto.CheckInRequest;
import com.utec.resetu.checkin.application.dto.CheckInResponse;
import com.utec.resetu.checkin.domain.model.CheckIn;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CheckInMapper {
    
    CheckIn toEntity(CheckInRequest request);
    
    CheckInResponse toResponse(CheckIn checkIn);
}
