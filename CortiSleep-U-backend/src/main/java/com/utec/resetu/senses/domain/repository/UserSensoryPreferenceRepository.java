package com.utec.resetu.senses.domain.repository;

import com.utec.resetu.senses.domain.model.UserSensoryPreference;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository

public interface UserSensoryPreferenceRepository extends JpaRepository<UserSensoryPreference, Long> {

    List<UserSensoryPreference> findByUser_IdAndFavoriteTrue(Long userId);

    Optional<UserSensoryPreference> findByUser_IdAndContent_Id(Long userId, Long contentId);

}

