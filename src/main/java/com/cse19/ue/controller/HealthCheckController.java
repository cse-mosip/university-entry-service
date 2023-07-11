package com.cse19.ue.controller;

import com.cse19.ue.dto.response.HealthCheckResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/health-check")
public class HealthCheckController {

    @GetMapping()
    public ResponseEntity<HealthCheckResponse> healthCheck() {

        return ResponseEntity.ok(
                HealthCheckResponse
                        .builder()
                        .message("Healthy")
                        .build()
        );
    }
}
