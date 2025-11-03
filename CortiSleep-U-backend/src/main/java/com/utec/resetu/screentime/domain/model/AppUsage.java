package com.utec.resetu.screentime.domain.model;

import jakarta.persistence.*;

import lombok.*;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

@Entity

@Table(name = "app_usage")

@EntityListeners(AuditingEntityListener.class)

public class AppUsage {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "screen_time_entry_id", nullable = false)

    private ScreenTimeEntry screenTimeEntry;

    @Column(nullable = false, length = 100)

    private String appName;

    @Column(length = 50)

    private String appCategory;

    @Column(nullable = false)

    private Integer usageMinutes;

    @CreatedDate

    @Column(nullable = false, updatable = false)

    private LocalDateTime createdAt;

}

