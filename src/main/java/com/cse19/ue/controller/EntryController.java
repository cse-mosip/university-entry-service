package com.cse19.ue.controller;

import com.cse19.ue.dto.request.GuestRegisterRequest;
import com.cse19.ue.dto.request.UserVerificationRequest;
import com.cse19.ue.service.EntryService;
import com.cse19.ue.service.UniversityEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.cse19.ue.dto.response.UserVerificationResponse;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/entry")
public class EntryController {
    private final EntryService entryService;
    private final UniversityEntryService universityEntryService;

    @GetMapping("/save-entry")
    public ResponseEntity<UserVerificationResponse> saveEntryLog(@RequestBody UserVerificationRequest request) {
        log.info("request received: " + request);
        return ResponseEntity.ok().body(entryService.saveEntryLog());
    }

    @ResponseBody
    @PostMapping(value = "/guest-register")
    public ResponseEntity<?> handleForm(@RequestBody GuestRegisterRequest request) {
        System.out.println(request.toString());
        return new ResponseEntity<>(universityEntryService.addGuest(request), HttpStatus.OK);
    }

}
