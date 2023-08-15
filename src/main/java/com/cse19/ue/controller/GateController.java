package com.cse19.ue.controller;

import com.cse19.ue.dto.request.EntryPlaceRegisterRequest;
import com.cse19.ue.dto.response.GetGatesResponse;
import com.cse19.ue.model.EntryPlace;
import com.cse19.ue.service.EntryPlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gates")
public class GateController {
    private final EntryPlaceService entryPlaceService;

    @GetMapping()
    public ResponseEntity<GetGatesResponse> getGates() {
        return ResponseEntity.ok().body(entryPlaceService.getAllEntryPlaces());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody EntryPlaceRegisterRequest request) {
        try {
            entryPlaceService.gateRegister(request);
            return ResponseEntity.ok("Gate registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
