package com.utec.resetu.gamification.domain.repository;

import com.utec.resetu.gamification.domain.model.Quest;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface QuestRepository extends JpaRepository<Quest, Long> {

    List<Quest> findByIsActiveTrueOrderByDifficultyAsc();

}

