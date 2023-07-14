package com.cse19.ue.controller;

import com.cse19.ue.dto.request.GuestRegisterRequest;
import com.cse19.ue.service.UniversityEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/entry")
public class UniversityEntryController {

    private UniversityEntryService universityEntryService;
    @Autowired
    public UniversityEntryController(UniversityEntryService universityEntryService){
        this.universityEntryService = universityEntryService;
    }

    @PostMapping(value = "/guest")
    public ResponseEntity<?> handleForm(@ModelAttribute GuestRegisterRequest request,
                             @RequestParam("approverId") String approverId, @RequestParam("bSign") Object bSign) {
        // get inviter details using bSign
        // return error if bSign not existing
       return new ResponseEntity<>(universityEntryService.addGuest(request,approverId,bSign), HttpStatus.OK);

    }
}
