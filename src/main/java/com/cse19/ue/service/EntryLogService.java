package com.cse19.ue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cse19.ue.model.UniversityEntryLog;
import com.cse19.ue.repository.EntryLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntryLogService {

    @Autowired
    private EntryLogRepository entryRepository;

    public void saveEntryLog() {
        // TODO: verfy from MOSIP
        UniversityEntryLog ueLog = new UniversityEntryLog();
        // create a sample entry log
        entryRepository.save(ueLog);
    }
}
