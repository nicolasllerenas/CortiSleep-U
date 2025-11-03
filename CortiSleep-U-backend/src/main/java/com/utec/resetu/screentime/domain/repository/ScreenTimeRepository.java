package com.utec.resetu.screentime.domain.repository;

import com.utec.resetu.screentime.domain.model.ScreenTimeEntry;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.List;

import java.util.Optional;

@Repository

public interface ScreenTimeRepository extends JpaRepository<ScreenTimeEntry, Long> {

    Optional<ScreenTimeEntry> findByUser_IdAndDate(Long userId, LocalDate date);

    List<ScreenTimeEntry> findByUser_IdAndDateBetweenOrderByDateDesc(Long userId, LocalDate start, LocalDate end);

    

    @Query("SELECT AVG(s.totalMinutes) FROM ScreenTimeEntry s WHERE s.user.id = :userId AND s.date BETWEEN :start AND :end")

    Double getAverageScreenTime(@Param("userId") Long userId, @Param("start") LocalDate start, @Param("end") LocalDate end);

}

