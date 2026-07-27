package com.jobtracker.controller;

import com.jobtracker.dto.AuthRequest;
import com.jobtracker.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok("Account created successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody AuthRequest request,
            HttpServletResponse response) {

        String token = authService.login(request.getUsername(), request.getPassword());

        // Write the JWT into an httpOnly cookie.
        // httpOnly = true means JavaScript cannot read this cookie (XSS protection).
        // The browser sends it automatically on every request to localhost:8080.
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400); // 24 hours in seconds
        // cookie.setSecure(true); // Enable this in production (requires HTTPS)
        response.addCookie(cookie);

        return ResponseEntity.ok("Logged in successfully.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // Clear the cookie by overwriting with one that expires immediately
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok("Logged out.");
    }
}


