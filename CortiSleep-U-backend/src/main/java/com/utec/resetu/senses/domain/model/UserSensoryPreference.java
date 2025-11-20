package com.utec.resetu.senses.domain.model;

import com.utec.resetu.auth.domain.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Table(name = "user_sensory_preferences")

@EntityListeners(AuditingEntityListener.class)

public class UserSensoryPreference {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id", nullable = false)

    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "content_id", nullable = false)

    private SensoryContent content;

    @Column(nullable = false)

    @Builder.Default

    private Boolean favorite = false;

    @Column(nullable = false)

    @Builder.Default

    private Integer playCount = 0;

    private LocalDateTime lastPlayedAt;

    private Integer rating;

    @CreatedDate

    @Column(nullable = false, updatable = false)

    private LocalDateTime createdAt;

    @LastModifiedDate

    @Column(nullable = false)

    private LocalDateTime updatedAt;

}

