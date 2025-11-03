package com.utec.resetu.senses.domain.model;

import jakarta.persistence.*;

import lombok.*;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data

@Builder

@NoArgsConstructor

@AllArgsConstructor

@Entity

@Table(name = "sensory_content")

@EntityListeners(AuditingEntityListener.class)

public class SensoryContent {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false, length = 100)

    private String title;

    @Column(columnDefinition = "TEXT")

    private String description;

    @Enumerated(EnumType.STRING)

    @Column(nullable = false, length = 20)

    private SenseType senseType;

    @Column(nullable = false, length = 500)

    private String contentUrl;

    @Column(length = 500)

    private String thumbnailUrl;

    private Integer durationSeconds;

    @Column(length = 200)

    private String tags;

    private Integer stressLevelMin;

    private Integer stressLevelMax;

    @Column(nullable = false)

    @Builder.Default

    private Boolean isActive = true;

    @Column(nullable = false)

    @Builder.Default

    private Integer viewCount = 0;

    @CreatedDate

    @Column(nullable = false, updatable = false)

    private LocalDateTime createdAt;

    @LastModifiedDate

    @Column(nullable = false)

    private LocalDateTime updatedAt;

}

