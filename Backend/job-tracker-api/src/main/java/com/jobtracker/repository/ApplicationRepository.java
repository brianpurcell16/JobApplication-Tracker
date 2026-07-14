package com.jobtracker.repository;

import com.jobtracker.model.Application;
import com.jobtracker.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ApplicationRepository extends   JpaRepository<Application, Long> {
    List<Application> findByUserIdOrderByIdDesc(Long userId);
    List<Application> findByUserIdAndStatus(Long userId, ApplicationStatus status);
    boolean existsByIdAndUserId(Long id, Long userId);
}
