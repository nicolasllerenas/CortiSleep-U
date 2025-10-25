package com.utec.resetu.checkin.application.service;

import com.utec.resetu.checkin.application.dto.CheckInRequest;
import com.utec.resetu.checkin.application.dto.CheckInResponse;
import com.utec.resetu.checkin.application.dto.CheckInStatsDto;
import com.utec.resetu.checkin.application.mapper.CheckInMapper;
import com.utec.resetu.checkin.domain.model.CheckIn;
import com.utec.resetu.checkin.domain.repository.CheckInRepository;
import com.utec.resetu.shared.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckInService {
    
    private final CheckInRepository checkInRepository;
    private final CheckInMapper checkInMapper;
    
    public CheckInResponse createCheckIn(CheckInRequest request) {
        // TODO: Obtener userId del contexto de seguridad
        Long userId = 1L; // Placeholder
        
        CheckIn checkIn = checkInMapper.toEntity(request);
        checkIn.setUserId(userId);
        
        CheckIn savedCheckIn = checkInRepository.save(checkIn);
        return checkInMapper.toResponse(savedCheckIn);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<CheckInResponse> getMyCheckIns(Pageable pageable) {
        // TODO: Obtener userId del contexto de seguridad
        Long userId = 1L; // Placeholder
        
        Page<CheckIn> checkIns = checkInRepository.findByUserIdOrderByCheckInTimeDesc(userId, pageable);
        
        List<CheckInResponse> responses = checkIns.getContent()
                .stream()
                .map(checkInMapper::toResponse)
                .collect(Collectors.toList());
        
        return PageResponse.<CheckInResponse>builder()
                .content(responses)
                .totalElements(checkIns.getTotalElements())
                .totalPages(checkIns.getTotalPages())
                .size(checkIns.getSize())
                .number(checkIns.getNumber())
                .build();
    }
    
    @Transactional(readOnly = true)
    public CheckInStatsDto getCheckInStats() {
        // TODO: Obtener userId del contexto de seguridad
        Long userId = 1L; // Placeholder
        
        List<CheckIn> allCheckIns = checkInRepository.findByUserIdOrderByCheckInTimeDesc(userId);
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime startOfWeek = now.minusDays(7);
        LocalDateTime startOfMonth = now.minusDays(30);
        
        long checkInsToday = checkInRepository.findByUserIdAndCheckInTimeBetween(userId, startOfDay, now).size();
        long checkInsThisWeek = checkInRepository.findByUserIdAndCheckInTimeBetween(userId, startOfWeek, now).size();
        long checkInsThisMonth = checkInRepository.findByUserIdAndCheckInTimeBetween(userId, startOfMonth, now).size();
        
        Double averageMoodScore = checkInRepository.findAverageMoodScoreByUserId(userId);
        Double averageStressLevel = checkInRepository.findAverageStressLevelByUserId(userId);
        
        List<CheckInResponse> recentCheckIns = allCheckIns.stream()
                .limit(5)
                .map(checkInMapper::toResponse)
                .collect(Collectors.toList());
        
        LocalDateTime lastCheckIn = allCheckIns.isEmpty() ? null : allCheckIns.get(0).getCheckInTime();
        
        return CheckInStatsDto.builder()
                .userId(userId)
                .totalCheckIns((long) allCheckIns.size())
                .averageMoodScore(averageMoodScore)
                .averageStressLevel(averageStressLevel)
                .averageEnergyLevel(null) // TODO: Implementar cálculo
                .checkInsToday(checkInsToday)
                .checkInsThisWeek(checkInsThisWeek)
                .checkInsThisMonth(checkInsThisMonth)
                .recentCheckIns(recentCheckIns)
                .lastCheckIn(lastCheckIn)
                .build();
    }
    
    @Transactional(readOnly = true)
    public List<CheckInResponse> getTodayCheckIns() {
        // TODO: Obtener userId del contexto de seguridad
        Long userId = 1L; // Placeholder
        
        List<CheckIn> todayCheckIns = checkInRepository.findTodayCheckInsByUserId(userId);
        
        return todayCheckIns.stream()
                .map(checkInMapper::toResponse)
                .collect(Collectors.toList());
    }
}
