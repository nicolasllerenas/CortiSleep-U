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

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

@Entity

@Table(name = "focus_sessions")

@EntityListeners(AuditingEntityListener.class)

public class FocusSession {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id", nullable = false)

    private User user;

    @Column(nullable = false)

    private Integer durationMinutes;

    @Column(nullable = false)

    @Builder.Default

    private Boolean completed = false;

    @Column(nullable = false)

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    @Column(length = 20, nullable = false)

    @Builder.Default

    private String sessionType = "POMODORO";

    @Column(length = 200)

    private String taskDescription;

    @CreatedDate

    @Column(nullable = false, updatable = false)

    private LocalDateTime createdAt;

}

