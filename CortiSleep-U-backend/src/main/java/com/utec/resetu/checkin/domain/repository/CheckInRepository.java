package com.utec.resetu.checkin.domain.repository;

import com.utec.resetu.checkin.domain.model.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    
    List<CheckIn> findByUserIdOrderByCheckInTimeDesc(Long userId);
    
    List<CheckIn> findByUserIdAndCheckInTimeBetween(Long userId, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT c FROM CheckIn c WHERE c.userId = :userId AND DATE(c.checkInTime) = CURRENT_DATE")
    List<CheckIn> findTodayCheckInsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT AVG(c.moodScore) FROM CheckIn c WHERE c.userId = :userId AND c.moodScore IS NOT NULL")
    Double findAverageMoodScoreByUserId(@Param("userId") Long userId);
    
    @Query("SELECT AVG(c.stressLevel) FROM CheckIn c WHERE c.userId = :userId AND c.stressLevel IS NOT NULL")
    Double findAverageStressLevelByUserId(@Param("userId") Long userId);
}
