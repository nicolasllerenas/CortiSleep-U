package com.utec.resetu.poi.application.service;

import com.utec.resetu.auth.domain.model.User;

import com.utec.resetu.auth.domain.repository.UserRepository;

import com.utec.resetu.poi.application.dto.*;

import com.utec.resetu.poi.domain.model.*;

import com.utec.resetu.poi.domain.repository.*;

import com.utec.resetu.profile.application.service.ProfileService;

import com.utec.resetu.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.util.Comparator;

import java.util.List;

import java.util.stream.Collectors;

@Slf4j

@Service

@RequiredArgsConstructor

public class POIService {

    private final POIRepository poiRepository;

    private final UserPOIVisitRepository visitRepository;

    private final UserRepository userRepository;

    private final GeolocationService geoService;

    private final ProfileService profileService;

    @Value("${app.points.poi-visit:25}")

    private int poiVisitPoints;

    @Transactional(readOnly = true)

    public List<POIResponse> getAllPOIs() {

        return poiRepository.findByIsActiveTrueOrderByNameAsc().stream()

                .map(this::toDto)

                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)

    public POIResponse getPOIById(Long id) {

        PointOfInterest poi = poiRepository.findById(id)

                .orElseThrow(() -> new ResourceNotFoundException("POI no encontrado"));

        return toDto(poi);

    }

    @Transactional(readOnly = true)

    public List<POIResponse> getNearbyPOIs(NearbyPOIRequest request) {

        List<PointOfInterest> allPOIs = poiRepository.findByIsActiveTrueOrderByNameAsc();

        return allPOIs.stream()

                .map(poi -> {

                    double distance = geoService.calculateDistance(

                            BigDecimal.valueOf(request.getLatitude()),

                            BigDecimal.valueOf(request.getLongitude()),

                            poi.getLatitude(),

                            poi.getLongitude()

                    );

                    POIResponse dto = toDto(poi);

                    dto.setDistanceKm(Math.round(distance * 100.0) / 100.0);

                    return dto;

                })

                .filter(dto -> dto.getDistanceKm() <= request.getRadiusKm())

                .sorted(Comparator.comparingDouble(POIResponse::getDistanceKm))

                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)

    public List<POIResponse> searchPOIs(String query) {

        return poiRepository.searchByQuery(query).stream()

                .map(this::toDto)

                .collect(Collectors.toList());

    }

    @Transactional

    public void visitPOI(Long userId, Long poiId, VisitPOIRequest request) {

        User user = userRepository.findById(userId)

                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        

        PointOfInterest poi = poiRepository.findById(poiId)

                .orElseThrow(() -> new ResourceNotFoundException("POI no encontrado"));

        UserPOIVisit visit = UserPOIVisit.builder()

                .user(user)

                .poi(poi)

                .durationMinutes(request.getDurationMinutes())

                .rating(request.getRating())

                .comment(request.getComment())

                .pointsEarned(poiVisitPoints)

                .build();

        visitRepository.save(visit);

        try {

            profileService.addPoints(userId, poiVisitPoints);

            log.info("Puntos otorgados por visitar POI: {}", poiVisitPoints);

        } catch (Exception e) {

            log.warn("No se pudieron otorgar puntos: {}", e.getMessage());

        }

    }

    @Transactional(readOnly = true)

    public Page<UserPOIVisit> getMyVisits(Long userId, Pageable pageable) {

        return visitRepository.findByUser_IdOrderByVisitedAtDesc(userId, pageable);

    }

    private POIResponse toDto(PointOfInterest poi) {

        return POIResponse.builder()

                .id(poi.getId())

                .name(poi.getName())

                .description(poi.getDescription())

                .category(poi.getCategory().name())

                .latitude(poi.getLatitude())

                .longitude(poi.getLongitude())

                .address(poi.getAddress())

                .imageUrl(poi.getImageUrl())

                .benefits(poi.getBenefits())

                .openingHours(poi.getOpeningHours())

                .university(poi.getUniversity())

                .pointsReward(poi.getPointsReward())

                .averageRating(poi.getAverageRating())

                .totalRatings(poi.getTotalRatings())

                .build();

    }

}

