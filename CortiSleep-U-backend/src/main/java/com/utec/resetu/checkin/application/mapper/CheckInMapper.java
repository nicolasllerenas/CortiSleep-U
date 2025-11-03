package com.utec.resetu.checkin.application.mapper;

import com.utec.resetu.checkin.application.dto.CheckInRequest;
import com.utec.resetu.checkin.application.dto.CheckInResponse;
import com.utec.resetu.checkin.domain.model.CheckIn;
import org.springframework.stereotype.Component;

@Component
public class CheckInMapper {

    public CheckIn toEntity(CheckInRequest request) {
        if (request == null) return null;
        return CheckIn.builder()
                .locationName(request.getLocationName())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .moodScore(request.getMoodScore())
                .stressLevel(request.getStressLevel())
                .energyLevel(request.getEnergyLevel())
                .notes(request.getNotes())
                .build();
    }

    public CheckInResponse toResponse(CheckIn checkIn) {
        if (checkIn == null) return null;
        return CheckInResponse.builder()
                .id(checkIn.getId())
                .userId(checkIn.getUserId())
                .locationName(checkIn.getLocationName())
                .latitude(checkIn.getLatitude())
                .longitude(checkIn.getLongitude())
                .moodScore(checkIn.getMoodScore())
                .stressLevel(checkIn.getStressLevel())
                .energyLevel(checkIn.getEnergyLevel())
                .notes(checkIn.getNotes())
                .checkInTime(checkIn.getCheckInTime())
                .createdAt(checkIn.getCreatedAt())
                .updatedAt(checkIn.getUpdatedAt())
                .build();
    }
}