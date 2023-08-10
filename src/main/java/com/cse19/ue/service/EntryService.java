package com.cse19.ue.service;

import com.cse19.ue.dto.response.EntranceRecordsResponse;
import com.cse19.ue.dto.response.UserVerificationResponse;
import com.cse19.ue.model.UniversityEntryLog;
import com.cse19.ue.model.User;
import com.cse19.ue.repository.ExtendedEntryLogRepository;
import com.cse19.ue.repository.EntryLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntryService {
    private final EntryLogRepository entryLogRepository;
    private final ExtendedEntryLogRepository extendedEntryLogRepository;

    public EntranceRecordsResponse entranceRecords(
            String index,
            String faculty,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            int skip,
            int take
    ) {

        return extendedEntryLogRepository.filterEntranceLogs(index, faculty, fromDate, toDate, skip, take);
    }


    public UserVerificationResponse saveEntryLog( Object bioData ) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            String uri = "http://localhost:7291/reg/rcapture"; //AuthServer address

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

            HttpEntity<Object> httpEntity = new HttpEntity<>(bioData, headers);
            ResponseEntity<?> authResult = restTemplate.exchange(uri, HttpMethod.GET,httpEntity, Object.class);

            Object result = authResult.getBody();
            //return result;

            // TODO: verify from auth server

            UniversityEntryLog universityEntryLog = new UniversityEntryLog();
            if (result != null) {
                universityEntryLog.setIndex( ((User) result).getId().toString() );
                universityEntryLog.setTimestamp( LocalDateTime.now() );
                universityEntryLog.setApprover( (User) result );
                universityEntryLog.setEntryPlace( null );
                universityEntryLog.setState( null );
                entryLogRepository.save(universityEntryLog);
                return new UserVerificationResponse( ((User) result).getName(), ((User) result).getId().toString(), "verified");
            }
            else{
                throw new Exception();
            }
        } catch (Exception e) {

            return new UserVerificationResponse("kumara", "190999A", "failed");

        }
    }

}