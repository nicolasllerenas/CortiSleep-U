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
        Long userId = 1L;
        CheckIn checkIn = checkInMapper.toEntity(request);
        checkIn.setUserId(userId);
        CheckIn savedCheckIn = checkInRepository.save(checkIn);
        return checkInMapper.toResponse(savedCheckIn);
    }

    public CheckInResponse createCheckIn(Long userId, CheckInRequest request) {
        CheckIn checkIn = checkInMapper.toEntity(request);
        checkIn.setUserId(userId);
        CheckIn savedCheckIn = checkInRepository.save(checkIn);
        return checkInMapper.toResponse(savedCheckIn);
    }
    
    @Transactional(readOnly = true)
    public PageResponse<CheckInResponse> getMyCheckIns(Pageable pageable) {
        // TODO: Obtener userId del contexto de seguridad
        Long userId = 1L; // Placeholder

        List<CheckIn> allCheckIns = checkInRepository.findByUserIdOrderByCheckInTimeDesc(userId);
        
        Page<CheckIn> checkInPage = new org.springframework.data.domain.PageImpl<>(
                allCheckIns,
                pageable,
                allCheckIns.size()
        );
        
        Page<CheckIn> checkIns = checkInPage;
        
        List<CheckInResponse> responses = checkIns.getContent()
                .stream()
                .map(checkInMapper::toResponse)
                .collect(Collectors.toList());
        
        return PageResponse.<CheckInResponse>builder()
                .content(responses)
                .totalElements(checkIns.getTotalElements())
                .totalPages(checkIns.getTotalPages())
                .size(checkIns.getSize())
                .totalElements(checkIns.getTotalElements())
                .build();
    }

    public Page<CheckInResponse> getCheckInsByUser(Long userId, Pageable pageable) {
        List<CheckIn> allCheckIns = checkInRepository.findByUserIdOrderByCheckInTimeDesc(userId);
        Page<CheckIn> page = new org.springframework.data.domain.PageImpl<>(allCheckIns, pageable, allCheckIns.size());
        return page.map(checkInMapper::toResponse);
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

    public CheckInStatsDto getStats(Long userId, LocalDate start, LocalDate end) {
        List<CheckIn> allCheckIns = checkInRepository.findByUserIdOrderByCheckInTimeDesc(userId);
        long checkInsInRange = checkInRepository.findByUserIdAndCheckInTimeBetween(userId, start.atStartOfDay(), end.plusDays(1).atStartOfDay()).size();
        Double averageMoodScore = checkInRepository.findAverageMoodScoreByUserId(userId);
        Double averageStressLevel = checkInRepository.findAverageStressLevelByUserId(userId);
        LocalDateTime lastCheckIn = allCheckIns.isEmpty() ? null : allCheckIns.get(0).getCheckInTime();
        return CheckInStatsDto.builder()
                .userId(userId)
                .totalCheckIns((long) allCheckIns.size())
                .averageMoodScore(averageMoodScore)
                .averageStressLevel(averageStressLevel)
                .checkInsThisMonth(checkInsInRange)
                .lastCheckIn(lastCheckIn)
                .build();
    }

    public CheckInResponse getCheckInById(Long id) {
        CheckIn checkIn = checkInRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("CheckIn no encontrado"));
        return checkInMapper.toResponse(checkIn);
    }

    public CheckInResponse getCheckInByDate(Long userId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        List<CheckIn> list = checkInRepository.findByUserIdAndCheckInTimeBetween(userId, start, end);
        if (list.isEmpty()) throw new IllegalArgumentException("CheckIn no encontrado para la fecha");
        return checkInMapper.toResponse(list.get(0));
    }

    public List<CheckInResponse> getCheckInsByDateRange(Long userId, LocalDate start, LocalDate end) {
        List<CheckIn> list = checkInRepository.findByUserIdAndCheckInTimeBetween(userId, start.atStartOfDay(), end.plusDays(1).atStartOfDay());
        return list.stream().map(checkInMapper::toResponse).collect(java.util.stream.Collectors.toList());
    }

    public CheckInResponse updateCheckIn(Long id, CheckInRequest request) {
        CheckIn checkIn = checkInRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("CheckIn no encontrado"));
        CheckIn updated = checkInMapper.toEntity(request);
        updated.setId(checkIn.getId());
        updated.setUserId(checkIn.getUserId());
        CheckIn saved = checkInRepository.save(updated);
        return checkInMapper.toResponse(saved);
    }

    public void deleteCheckIn(Long id) {
        checkInRepository.deleteById(id);
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
