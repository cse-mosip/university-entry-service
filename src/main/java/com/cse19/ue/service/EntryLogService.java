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

    public void saveEntryLog(UniversityEntryLog entrylog) {
        // TODO: verfy from MOSIP
        entryRepository.save(entrylog);
    }
}
