package com.utec.resetu.poi.domain.repository;

import com.utec.resetu.poi.domain.model.POICategory;

import com.utec.resetu.poi.domain.model.PointOfInterest;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface POIRepository extends JpaRepository<PointOfInterest, Long> {

    List<PointOfInterest> findByIsActiveTrueOrderByNameAsc();

    List<PointOfInterest> findByCategoryAndIsActiveTrue(POICategory category);

    List<PointOfInterest> findByUniversityAndIsActiveTrue(String university);

    @Query("SELECT p FROM PointOfInterest p WHERE p.isActive = true AND " +

           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +

           "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')))")

    List<PointOfInterest> searchByQuery(@Param("query") String query);

}

