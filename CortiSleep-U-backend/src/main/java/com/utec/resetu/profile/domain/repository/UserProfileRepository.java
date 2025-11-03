package com.utec.resetu.profile.domain.repository;

import com.utec.resetu.profile.domain.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    @Modifying
    @Query("UPDATE UserProfile p SET p.totalPoints = p.totalPoints + :points WHERE p.user.id = :userId")
    void addPoints(@Param("userId") Long userId, @Param("points") Integer points);

    @Modifying
    @Query("UPDATE UserProfile p SET p.totalPoints = p.totalPoints - :points WHERE p.user.id = :userId AND p.totalPoints >= :points")
    int deductPoints(@Param("userId") Long userId, @Param("points") Integer points);

    @Query("SELECT p.totalPoints FROM UserProfile p WHERE p.user.id = :userId")
    Optional<Integer> findTotalPointsByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(p) FROM UserProfile p WHERE p.faculty = :faculty")
    long countByFaculty(@Param("faculty") String faculty);
}
