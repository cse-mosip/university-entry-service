package com.cse19.ue.controller;


import com.cse19.ue.model.EntryPlace;
import com.cse19.ue.service.EntryPlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/entry-register")
public class EntryRegisterController {
    @Autowired
    private EntryPlaceService entryPlaceService;
    @PostMapping()
    public ResponseEntity<String> registerUser(@RequestBody EntryPlace entryPlace) {
        try {
            entryPlaceService.registerUser(entryPlace);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
