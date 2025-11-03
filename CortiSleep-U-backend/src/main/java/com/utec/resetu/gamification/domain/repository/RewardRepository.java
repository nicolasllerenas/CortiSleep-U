package com.utec.resetu.gamification.domain.repository;

import com.utec.resetu.gamification.domain.model.Reward;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface RewardRepository extends JpaRepository<Reward, Long> {

    List<Reward> findByIsActiveTrueOrderByPointsCostAsc();

}

