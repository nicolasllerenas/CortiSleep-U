package com.utec.resetu.screentime.domain.repository;

import com.utec.resetu.screentime.domain.model.AppUsage;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AppUsageRepository extends JpaRepository<AppUsage, Long> {

    List<AppUsage> findByScreenTimeEntryId(Long screenTimeEntryId);

}

