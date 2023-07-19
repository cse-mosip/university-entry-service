package com.cse19.ue.controller;

import com.cse19.ue.dto.response.EntranceRecordsResponse;
import com.cse19.ue.dto.response.PersonInfo;
import com.cse19.ue.service.EntryService;
import com.cse19.ue.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/info")
public class InfoController {
    private final EntryService entryService;
    private final PersonService personService;

    @GetMapping("/{index}")
    public ResponseEntity<PersonInfo> entranceRecordsByIndex(@PathVariable String index) {
//        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = "sdfswedfsd-security-token"; //jwt.getTokenValue();
        return ResponseEntity.ok().body(personService.personInfo(index, token));
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

//        check skip has to be greater than if not set to zero
        if (skip == null || skip < 0) {
            skip = 0;
        }
//        check take has to be greater than zero and less than 100 if not set to 100
        if (take == null || take < 0 || take > 100) {
            take = 100;
        }


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

        return ResponseEntity.ok().body(entryService.entranceRecords(index, faculty, fromDate, toDate, skip, take));
    }

}
