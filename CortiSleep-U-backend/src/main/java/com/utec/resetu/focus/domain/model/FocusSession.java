package com.utec.resetu.focus.domain.model;

import com.utec.resetu.auth.domain.model.User;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity

@Table(name = "focus_sessions")

@EntityListeners(AuditingEntityListener.class)

@Data
public class FocusSession {
    public FocusSession() {}
    public FocusSession(Long id, com.utec.resetu.auth.domain.model.User user, Integer durationMinutes, Boolean completed, java.time.LocalDateTime startedAt, java.time.LocalDateTime endedAt, String sessionType, String taskDescription, java.time.LocalDateTime createdAt) {
        this.id=id; this.user=user; this.durationMinutes=durationMinutes; this.completed=completed; this.startedAt=startedAt; this.endedAt=endedAt; this.sessionType=sessionType; this.taskDescription=taskDescription; this.createdAt=createdAt;
    }

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id", nullable = false)

    private User user;

    @Column(nullable = false)

    private Integer durationMinutes;

    @Column(nullable = false)

    private Boolean completed = false;

    @Column(nullable = false)

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @Column(length = 20, nullable = false)

    private String sessionType = "POMODORO";

    @Column(length = 200)

    private String taskDescription;

    @CreatedDate

    @Column(nullable = false, updatable = false)

    private LocalDateTime createdAt;

}

