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
        UserProfile p = new UserProfile();
        p.setAlias(request.getAlias());
        p.setFaculty(stringToFaculty(request.getFaculty()));
        p.setSemester(request.getSemester());
        p.setCareer(request.getCareer());
        p.setBio(request.getBio());
        p.setAvatarUrl(request.getAvatarUrl());
        p.setBirthDate(request.getBirthDate());
        p.setStressLevel(request.getStressLevel());
        p.setSleepGoalHours(request.getSleepGoalHours());
        p.setScreenTimeLimitMinutes(request.getScreenTimeLimitMinutes());
        p.setPreferredSenseType(request.getPreferredSenseType());
        return p;
    }

    public ProfileResponse toDto(UserProfile profile) {
        if (profile == null) return null;
        ProfileResponse r = new ProfileResponse();
        r.setId(profile.getId());
        r.setUserId(profile.getUser() != null ? profile.getUser().getId() : null);
        r.setUserEmail(profile.getUser() != null ? profile.getUser().getEmail() : null);
        r.setFullName(profile.getUser() != null ? profile.getUser().getFullName() : null);
        r.setAlias(profile.getAlias());
        r.setFaculty(facultyToString(profile.getFaculty()));
        r.setSemester(profile.getSemester());
        r.setCareer(profile.getCareer());
        r.setBio(profile.getBio());
        r.setAvatarUrl(profile.getAvatarUrl());
        r.setBirthDate(profile.getBirthDate());
        r.setAge(calculateAge(profile.getBirthDate()));
        r.setTotalPoints(profile.getTotalPoints());
        r.setStressLevel(profile.getStressLevel());
        r.setSleepGoalHours(profile.getSleepGoalHours());
        r.setScreenTimeLimitMinutes(profile.getScreenTimeLimitMinutes());
        r.setPreferredSenseType(profile.getPreferredSenseType());
        r.setCreatedAt(profile.getCreatedAt() != null ? profile.getCreatedAt().toString() : null);
        return r;
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
