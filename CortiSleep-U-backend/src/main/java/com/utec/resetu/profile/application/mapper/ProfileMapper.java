package com.utec.resetu.profile.application.mapper;

import com.utec.resetu.profile.application.dto.ProfileRequest;
import com.utec.resetu.profile.application.dto.ProfileResponse;
import com.utec.resetu.profile.domain.model.Faculty;
import com.utec.resetu.profile.domain.model.UserProfile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class ProfileMapper {

    public UserProfile toEntity(ProfileRequest request) {
        if (request == null) return null;
        UserProfile.UserProfileBuilder builder = UserProfile.builder()
                .alias(request.getAlias())
                .faculty(stringToFaculty(request.getFaculty()))
                .semester(request.getSemester())
                .career(request.getCareer())
                .bio(request.getBio())
                .avatarUrl(request.getAvatarUrl())
                .birthDate(request.getBirthDate())
                .stressLevel(request.getStressLevel())
                .sleepGoalHours(request.getSleepGoalHours())
                .screenTimeLimitMinutes(request.getScreenTimeLimitMinutes())
                .preferredSenseType(request.getPreferredSenseType());
        return builder.build();
    }

    public ProfileResponse toDto(UserProfile profile) {
        if (profile == null) return null;
        return ProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUser() != null ? profile.getUser().getId() : null)
                .userEmail(profile.getUser() != null ? profile.getUser().getEmail() : null)
                .fullName(profile.getUser() != null ? profile.getUser().getFullName() : null)
                .alias(profile.getAlias())
                .faculty(facultyToString(profile.getFaculty()))
                .semester(profile.getSemester())
                .career(profile.getCareer())
                .bio(profile.getBio())
                .avatarUrl(profile.getAvatarUrl())
                .birthDate(profile.getBirthDate())
                .age(calculateAge(profile.getBirthDate()))
                .totalPoints(profile.getTotalPoints())
                .stressLevel(profile.getStressLevel())
                .sleepGoalHours(profile.getSleepGoalHours())
                .screenTimeLimitMinutes(profile.getScreenTimeLimitMinutes())
                .preferredSenseType(profile.getPreferredSenseType())
                .createdAt(profile.getCreatedAt() != null ? profile.getCreatedAt().toString() : null)
                .build();
    }

    public void updateEntityFromDto(ProfileRequest request, UserProfile profile) {
        if (request == null || profile == null) return;
        if (request.getAlias() != null) profile.setAlias(request.getAlias());
        if (request.getFaculty() != null) profile.setFaculty(stringToFaculty(request.getFaculty()));
        if (request.getSemester() != null) profile.setSemester(request.getSemester());
        if (request.getCareer() != null) profile.setCareer(request.getCareer());
        if (request.getBio() != null) profile.setBio(request.getBio());
        if (request.getAvatarUrl() != null) profile.setAvatarUrl(request.getAvatarUrl());
        if (request.getBirthDate() != null) profile.setBirthDate(request.getBirthDate());
        if (request.getStressLevel() != null) profile.setStressLevel(request.getStressLevel());
        if (request.getSleepGoalHours() != null) profile.setSleepGoalHours(request.getSleepGoalHours());
        if (request.getScreenTimeLimitMinutes() != null) profile.setScreenTimeLimitMinutes(request.getScreenTimeLimitMinutes());
        if (request.getPreferredSenseType() != null) profile.setPreferredSenseType(request.getPreferredSenseType());
    }

    private Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) return null;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private String facultyToString(Faculty faculty) {
        return faculty != null ? faculty.name() : null;
    }

    private Faculty stringToFaculty(String faculty) {
        if (faculty == null || faculty.isEmpty()) return null;
        try { return Faculty.valueOf(faculty); } catch (IllegalArgumentException e) { return null; }
    }
}
