package com.cse19.ue.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cse19.ue.model.UniversityEntryLog;
import com.cse19.ue.service.EntryLogService;

// this controller will be removed/ renamed

@RestController
@RequestMapping(value = "ue")
public class EntryLogController {

    @Autowired
    private EntryLogService entryLogService; 

    @GetMapping("/saveEntryLog")
    public String saveEntryLog(@RequestBody UniversityEntryLog entrylog) {
        entryLogService.saveEntryLog(entrylog);
        return String.format("saved..");
    }
}
