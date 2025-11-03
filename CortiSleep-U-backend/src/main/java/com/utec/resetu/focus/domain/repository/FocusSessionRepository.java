package com.utec.resetu.focus.domain.repository;

import com.utec.resetu.focus.domain.model.FocusSession;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository

public interface FocusSessionRepository extends JpaRepository<FocusSession, Long> {

    Page<FocusSession> findByUser_IdOrderByStartedAtDesc(Long userId, Pageable pageable);

    @Query("SELECT COUNT(f) FROM FocusSession f WHERE f.user.id = :userId AND f.completed = true")

    long countCompletedSessions(@Param("userId") Long userId);

    @Query("SELECT SUM(f.durationMinutes) FROM FocusSession f WHERE f.user.id = :userId AND f.completed = true")

    Long getTotalFocusMinutes(@Param("userId") Long userId);

    @Query("SELECT COUNT(f) FROM FocusSession f WHERE f.user.id = :userId AND f.startedAt >= :since AND f.completed = true")

    long countCompletedSessionsSince(@Param("userId") Long userId, @Param("since") LocalDateTime since);

}

