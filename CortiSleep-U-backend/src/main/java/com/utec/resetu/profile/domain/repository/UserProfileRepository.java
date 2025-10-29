package com.utec.resetu.profile.domain.repository;

import com.utec.resetu.profile.domain.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    
    Optional<UserProfile> findByUserId(Long userId);
    
    boolean existsByUserId(Long userId);
    
    boolean existsByStudentCode(String studentCode);
}
