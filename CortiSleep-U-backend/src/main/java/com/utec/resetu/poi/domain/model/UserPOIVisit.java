package com.utec.resetu.poi.domain.model;

import com.utec.resetu.auth.domain.model.User;

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

@Table(name = "user_poi_visits")

@EntityListeners(AuditingEntityListener.class)

public class UserPOIVisit {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id", nullable = false)

    private User user;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "poi_id", nullable = false)

    private PointOfInterest poi;

    @CreatedDate

    @Column(nullable = false, updatable = false)

    private LocalDateTime visitedAt;

    @Column(nullable = false)

    @Builder.Default

    private Integer pointsEarned = 10;

    private Integer durationMinutes;

    private Integer rating;

    @Column(columnDefinition = "TEXT")

    private String comment;

}

