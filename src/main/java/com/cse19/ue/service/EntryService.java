package com.cse19.ue.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cse19.ue.dto.response.UserVerificationResponse;
import com.cse19.ue.model.EntryPlace;
import com.cse19.ue.model.EntryState;
import com.cse19.ue.model.Role;
import com.cse19.ue.model.UniversityEntryLog;
import com.cse19.ue.model.User;
import com.cse19.ue.repository.EntryLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntryService {

    @Autowired
    private EntryLogRepository entryLogRepository;

    public EntryService(EntryLogRepository entryLogRepository) {
        this.entryLogRepository = entryLogRepository;
    }


    public UserVerificationResponse saveEntryLog() {
        try {
        
            // TODO: verify from auth server

            UniversityEntryLog universityEntryLog = new UniversityEntryLog();
            entryLogRepository.save(universityEntryLog);

            return new UserVerificationResponse("kumara", "190999A", "verified");
        } catch (Exception e) {

            return new UserVerificationResponse("kumara", "190999A", "failed");

        }
    }
}
