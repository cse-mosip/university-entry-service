package com.cse19.ue.controller;

import com.cse19.ue.dto.request.LoginRequest;
import com.cse19.ue.dto.request.UserRegisterRequest;
import com.cse19.ue.dto.response.AuthResponse;
import com.cse19.ue.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.created(null)
                .body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok()
                .body(authService.login(request));
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {

        return ResponseEntity.ok()
                .body("logout successful.");
    }
}
