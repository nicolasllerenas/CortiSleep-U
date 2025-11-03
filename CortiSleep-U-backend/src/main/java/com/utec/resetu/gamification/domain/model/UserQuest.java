package com.utec.resetu.gamification.domain.model;

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

@Table(name = "user_quests")

@EntityListeners(AuditingEntityListener.class)

public class UserQuest {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "user_id", nullable = false)

    private User user;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "quest_id", nullable = false)

    private Quest quest;

    @Column(nullable = false)

    @Builder.Default

    private Integer currentProgress = 0;

    @Column(nullable = false, length = 20)

    @Builder.Default

    private String status = "IN_PROGRESS";

    @CreatedDate

    @Column(nullable = false, updatable = false)

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

}

