package com.cse19.ue.controller;

import com.cse19.ue.dto.response.EntranceRecordsResponse;
import com.cse19.ue.service.EntryServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final EntryServices entryServices;

    @GetMapping("/register")
    public String register() {
        return "hello";
    }

    @GetMapping("/entrance-records")
    public ResponseEntity<EntranceRecordsResponse> entranceRecords(
            @RequestParam(required = false) String index,
            @RequestParam(required = false) String faculty,
            @RequestParam(name = "from_date", required = false) String fromDateString,
            @RequestParam(name = "to_date", required = false) String toDateString,
            @RequestParam(required = false) Integer skip,
            @RequestParam(required = false) Integer take
    ) {
        log.warn("entranceRecords");


//        check skip has to be greater than if not set to zero
        if (skip == null || skip < 0) {
            skip = 0;
        }
//        check take has to be greater than zero and less than 100 if not set to 100
        if (take == null || take < 0 || take > 100) {
            take = 100;
        }

        log.warn("take :{}", take);

        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;

        try {
            if (fromDateString != null) {
                fromDate = LocalDateTime.parse(fromDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            if (toDateString != null) {
                toDate = LocalDateTime.parse(toDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        } catch (Exception e) {
            log.error("Error parsing date");
        }


//        if form date is greater than to date then return error
        if (fromDate != null && toDate != null && fromDate.isAfter(toDate))
            throw new IllegalArgumentException("From date cannot be greater than to date");


        return ResponseEntity.ok().body(entryServices.entranceRecords(index, faculty, fromDate, toDate, skip, take));
    }

}
