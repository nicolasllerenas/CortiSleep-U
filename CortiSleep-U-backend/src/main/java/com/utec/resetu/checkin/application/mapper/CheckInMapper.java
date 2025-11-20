package com.utec.resetu.checkin.application.mapper;

import com.utec.resetu.checkin.application.dto.CheckInRequest;
import com.utec.resetu.checkin.application.dto.CheckInResponse;
import com.utec.resetu.checkin.domain.model.CheckIn;
import org.springframework.stereotype.Component;

@Component
public class CheckInMapper {

    public CheckIn toEntity(CheckInRequest request) {
        if (request == null) return null;
        CheckIn ci = new CheckIn();
        ci.setLocationName(request.getLocationName());
        ci.setLatitude(request.getLatitude());
        ci.setLongitude(request.getLongitude());
        ci.setMoodScore(request.getMoodScore());
        ci.setStressLevel(request.getStressLevel());
        ci.setEnergyLevel(request.getEnergyLevel());
        ci.setNotes(request.getNotes());
        return ci;
    }

    /**
     * Update an existing CheckIn entity with values from the request. Only updates
     * editable fields; preserves date, checkInTime, createdAt and userId.
     */
    public void updateEntityFromRequest(CheckIn existing, CheckInRequest request) {
        if (existing == null || request == null) return;
        existing.setLocationName(request.getLocationName());
        existing.setLatitude(request.getLatitude());
        existing.setLongitude(request.getLongitude());
        existing.setMoodScore(request.getMoodScore());
        existing.setStressLevel(request.getStressLevel());
        existing.setEnergyLevel(request.getEnergyLevel());
        existing.setNotes(request.getNotes());
        // Do NOT overwrite createdAt, date, checkInTime or userId here.
    }

    public CheckInResponse toResponse(CheckIn checkIn) {
        if (checkIn == null) return null;
        CheckInResponse r = new CheckInResponse();
        r.setId(checkIn.getId());
        r.setUserId(checkIn.getUserId());
        r.setLocationName(checkIn.getLocationName());
        r.setLatitude(checkIn.getLatitude());
        r.setLongitude(checkIn.getLongitude());
        r.setMoodScore(checkIn.getMoodScore());
        r.setStressLevel(checkIn.getStressLevel());
        r.setEnergyLevel(checkIn.getEnergyLevel());
        r.setNotes(checkIn.getNotes());
    r.setDate(checkIn.getDate());
    r.setCheckInTime(checkIn.getCheckInTime());
        r.setCreatedAt(checkIn.getCreatedAt());
        r.setUpdatedAt(checkIn.getUpdatedAt());
        return r;
    }
}