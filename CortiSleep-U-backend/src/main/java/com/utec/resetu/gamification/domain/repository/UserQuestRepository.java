package com.utec.resetu.gamification.domain.repository;

import com.utec.resetu.gamification.domain.model.UserQuest;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository

public interface UserQuestRepository extends JpaRepository<UserQuest, Long> {

    List<UserQuest> findByUser_IdAndStatus(Long userId, String status);

    Optional<UserQuest> findByUser_IdAndQuest_Id(Long userId, Long questId);

}

