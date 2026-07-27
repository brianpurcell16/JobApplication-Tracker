package com.jobtracker.controller;

import com.jobtracker.dto.ApplicationRequest;
import com.jobtracker.model.Application;
import com.jobtracker.model.ApplicationStatus;
import com.jobtracker.repository.UserRepository;
import com.jobtracker.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Application>> getAll(
            @RequestParam(required = false) ApplicationStatus status) {
        Long userId = getCurrentUserId();
        List<Application> apps = status != null
                ? applicationService.getAllApplicationsByStatus(userId, status)
                : applicationService.getAllApplications(userId);
        return ResponseEntity.ok(apps);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getOwned(getCurrentUserId(), id));
    }

    @PostMapping
    public ResponseEntity<Application> create(@RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(applicationService.createApplication(getCurrentUserId(), request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> update(
            @PathVariable Long id, @RequestBody ApplicationRequest request) {
        return ResponseEntity.ok(applicationService.update(getCurrentUserId(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        applicationService.delete(getCurrentUserId(), id);
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found."))
                .getId();
    }
}

