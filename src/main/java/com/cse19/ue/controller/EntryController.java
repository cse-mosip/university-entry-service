package com.cse19.ue.controller;

import com.cse19.ue.dto.request.GuestRegisterRequest;
import com.cse19.ue.dto.request.SaveEntryRequest;
import com.cse19.ue.service.EntryService;
import com.cse19.ue.service.UniversityEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<UserVerificationResponse> saveEntryLog(@RequestBody SaveEntryRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the principal (user details) from the authentication
        String subject = authentication.getPrincipal().toString();
        UserVerificationResponse response = entryService.saveEntryLog(request, subject);
        return ResponseEntity.ok(response);

    }

    @ResponseBody
    @PostMapping(value = "/guest-register")
    public ResponseEntity<?> handleForm(@RequestBody GuestRegisterRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the principal (user details) from the authentication
        String subject = authentication.getPrincipal().toString();

        System.out.println("Access token subject: " + subject.toString());
        return new ResponseEntity<>(universityEntryService.addGuest(request, subject), HttpStatus.OK);
    }

}
