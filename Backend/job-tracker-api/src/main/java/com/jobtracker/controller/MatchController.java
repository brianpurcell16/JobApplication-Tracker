package com.jobtracker.controller;

import com.jobtracker.dto.MatchResultResponse;
import com.jobtracker.repository.UserRepository;
import com.jobtracker.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final UserRepository userRepository;

    @PostMapping("/{applicationId}")
    public ResponseEntity<MatchResultResponse> runMatch(@PathVariable Long applicationId) {
        return ResponseEntity.ok(matchService.runMatch(getCurrentUserId(), applicationId));
    }

    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found."))
                .getId();
    }
}

