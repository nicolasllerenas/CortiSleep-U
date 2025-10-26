package com.utec.resetu.profile.application.mapper;

import com.utec.resetu.profile.application.dto.ProfileRequest;
import com.utec.resetu.profile.application.dto.ProfileResponse;
import com.utec.resetu.profile.domain.model.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProfileMapper {
    
    UserProfile toEntity(ProfileRequest request);
    
    ProfileResponse toResponse(UserProfile profile);
    
    void updateEntity(ProfileRequest request, @MappingTarget UserProfile profile);
}
