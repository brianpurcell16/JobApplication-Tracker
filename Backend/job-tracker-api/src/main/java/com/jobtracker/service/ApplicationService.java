package com.jobtracker.service;


import com.jobtracker.dto.ApplicationRequest;
import com.jobtracker.model.*;
import com.jobtracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public List<Application> getAllApplications(Long UserId) {
        return applicationRepository.findByUserIdOrderByIdDesc(UserId);
    }

    public List<Application> getAllApplicationsByStatus(Long UserId, ApplicationStatus status) {
        return applicationRepository.findByUserIdAndStatus(UserId, status);
    }

    public Application createApplication(Long UserId, ApplicationRequest request) {
        User user = userRepository.getReferenceById(UserId);
        Application application = Application.builder()
                .user(user)
                .company(request.getComapny())
                .role(request.getRole())
                .location(request.getLocation())
                .jobUrl(request.getJobUrl())
                .jobDescription(request.getJobDescription())
                .status(ApplicationStatus.SAVED)
                .notes(request.getNotes())
                .build();
        return applicationRepository.save(application);
    }

    public Application update(Long userId, Long applicationId, ApplicationRequest request) {
        Application application = getOwned(userId, applicationId);
        application.setCompany(request.getCompany());
        application.setRole(request.getRole());
        application.setLocation(request.getLocation());
        application.setJobUrl(request.getJobUrl());
        application.setJobDescription(request.getJobDescription());
        application.setNotes(request.getNotes());
        if (request.getStatus() != null) {
            application.setStatus(request.getStatus());
        }
        if (request.getDateApplied() != null) {
            application.setDateApplied(request.getDateApplied());
        }
        return applicationRepository.save(application);
    }

    public void delete(Long userId, Long applicationId) {
        Application application = getOwned(userId, applicationId);
        applicationRepository.delete(application);
    }

    public Application getOwned(Long userId, Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found."));
        if (!application.getUser().getId().equals(userId)) {
            throw new SecurityException("You do not own this application.");
        }
        return application;
    }


}
