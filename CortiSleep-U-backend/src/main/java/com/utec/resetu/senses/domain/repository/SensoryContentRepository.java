package com.utec.resetu.senses.domain.repository;

import com.utec.resetu.senses.domain.model.SenseType;

import com.utec.resetu.senses.domain.model.SensoryContent;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface SensoryContentRepository extends JpaRepository<SensoryContent, Long> {

    List<SensoryContent> findByIsActiveTrueOrderByViewCountDesc();

    List<SensoryContent> findBySenseTypeAndIsActiveTrue(SenseType senseType);

    List<SensoryContent> findByStressLevelMinLessThanEqualAndStressLevelMaxGreaterThanEqualAndIsActiveTrue(

        Integer stressLevel, Integer stressLevel2

    );

}

