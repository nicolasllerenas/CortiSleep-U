package com.utec.resetu.profile.application.mapper;

import com.utec.resetu.profile.application.dto.ProfileRequest;

import com.utec.resetu.profile.application.dto.ProfileResponse;

import com.utec.resetu.profile.domain.model.UserProfile;

import org.mapstruct.*;

import java.time.LocalDate;

import java.time.Period;

@Mapper(componentModel = "spring")

public interface ProfileMapper {

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "user", ignore = true)

    @Mapping(target = "totalPoints", ignore = true)

    @Mapping(target = "createdAt", ignore = true)

    @Mapping(target = "updatedAt", ignore = true)

    @Mapping(target = "faculty", source = "faculty", qualifiedByName = "stringToFaculty")

    UserProfile toEntity(ProfileRequest request);

    @Mapping(target = "userId", source = "user.id")

    @Mapping(target = "userEmail", source = "user.email")

    @Mapping(target = "fullName", source = "user.fullName")

    @Mapping(target = "faculty", source = "faculty", qualifiedByName = "facultyToString")

    @Mapping(target = "age", source = "birthDate", qualifiedByName = "calculateAge")

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")

    ProfileResponse toDto(UserProfile profile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    @Mapping(target = "id", ignore = true)

    @Mapping(target = "user", ignore = true)

    @Mapping(target = "totalPoints", ignore = true)

    @Mapping(target = "createdAt", ignore = true)

    @Mapping(target = "updatedAt", ignore = true)

    @Mapping(target = "faculty", source = "faculty", qualifiedByName = "stringToFaculty")

    void updateEntityFromDto(ProfileRequest request, @MappingTarget UserProfile profile);

    @Named("calculateAge")

    default Integer calculateAge(LocalDate birthDate) {

        if (birthDate == null) return null;

        return Period.between(birthDate, LocalDate.now()).getYears();

    }

    @Named("facultyToString")

    default String facultyToString(com.utec.resetu.profile.domain.model.Faculty faculty) {

        return faculty != null ? faculty.name() : null;

    }

    @Named("stringToFaculty")

    default com.utec.resetu.profile.domain.model.Faculty stringToFaculty(String faculty) {

        if (faculty == null || faculty.isEmpty()) return null;

        try {

            return com.utec.resetu.profile.domain.model.Faculty.valueOf(faculty);

        } catch (IllegalArgumentException e) {

            return null;

        }

    }

}
