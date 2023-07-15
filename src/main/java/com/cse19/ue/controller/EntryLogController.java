package com.cse19.ue.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import com.cse19.ue.model.UniversityEntryLog;
import com.cse19.ue.service.EntryService;
import com.cse19.ue.dto.response.UserVerificationResponse;

// this controller will be removed/ renamed

@RestController
@RequestMapping(value = "/test")
public class EntryLogController {

    @Autowired
    private EntryService entryLogService; 

    public EntryLogController(EntryService entryService) {
        this.entryLogService = entryService;
    }

    @GetMapping("/saveEntryLog")
    public ResponseEntity<UserVerificationResponse> saveEntryLog() {
        return ResponseEntity.ok(entryLogService.saveEntryLog());
        
    }
}
