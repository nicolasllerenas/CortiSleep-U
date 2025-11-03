package com.utec.resetu.screentime.domain.model;

import com.utec.resetu.auth.domain.model.User;

import jakarta.persistence.*;

import lombok.*;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

import java.time.LocalDateTime;

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

@Entity

@Table(name = "screen_time_entries")

@EntityListeners(AuditingEntityListener.class)

public class ScreenTimeEntry {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id", nullable = false)

    private User user;

    @Column(nullable = false)

    private LocalDate date;

    @Column(nullable = false)

    private Integer totalMinutes;

    @Column(length = 20)

    private String deviceType;

    @CreatedDate

    @Column(nullable = false, updatable = false)

    private LocalDateTime createdAt;

}

