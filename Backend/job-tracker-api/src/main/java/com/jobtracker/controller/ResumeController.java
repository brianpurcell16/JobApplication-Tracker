package com.jobtracker.controller;

import com.jobtracker.model.ResumeProfile;
import com.jobtracker.repository.UserRepository;
import com.jobtracker.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final UserRepository userRepository;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ResumeProfile> upload(@RequestParam("file") MultipartFile file)
            throws IOException {
        return ResponseEntity.ok(resumeService.uploadResume(getCurrentUserId(), file));
    }

    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found."))
                .getId();
    }
}

