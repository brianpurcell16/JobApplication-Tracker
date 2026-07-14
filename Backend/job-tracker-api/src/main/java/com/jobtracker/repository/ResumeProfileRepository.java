package com.jobtracker.repository;

import com.jobtracker.model.ResumeProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeProfileRepository extends JpaRepository<ResumeProfile, Long> {
    Optional<ResumeProfile> findByUserId(Long userId);
}
