package com.utec.resetu.poi.domain.repository;

import com.utec.resetu.poi.domain.model.UserPOIVisit;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository

public interface UserPOIVisitRepository extends JpaRepository<UserPOIVisit, Long> {

    Page<UserPOIVisit> findByUser_IdOrderByVisitedAtDesc(Long userId, Pageable pageable);

    boolean existsByUser_IdAndPoi_Id(Long userId, Long poiId);

}

