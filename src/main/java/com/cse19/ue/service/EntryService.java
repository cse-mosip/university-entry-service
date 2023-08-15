package com.cse19.ue.service;


import com.cse19.ue.dto.AuthResponseDto;
import com.cse19.ue.dto.request.SaveEntryRequest;
import com.cse19.ue.dto.response.EntranceRecordsResponse;
import com.cse19.ue.dto.response.PersonInfo;
import com.cse19.ue.dto.response.UserVerificationResponse;
import com.cse19.ue.exception.DataSavingException;
import com.cse19.ue.exception.UserNotFoundException;
import com.cse19.ue.model.EntryPlace;
import com.cse19.ue.model.EntryState;
import com.cse19.ue.model.UniversityEntryLog;
import com.cse19.ue.repository.EntryPlaceRepository;
import com.cse19.ue.model.User;
import com.cse19.ue.repository.ExtendedEntryLogRepository;
import com.cse19.ue.repository.EntryLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final EntryPlaceRepository entryPlaceRepository;
    private final PersonService personService;

    private final String NO_MATCH_FOUND = "no match found";

    @Value("${services.authentication.url}")
    private String AUTHENTICATION_URL;

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


    public UserVerificationResponse saveEntryLog(SaveEntryRequest request, String subject) throws Exception {

        log.info("## request consumed");

        RestTemplate restTemplate = new RestTemplate();
        String uri = AUTHENTICATION_URL + "/upload"; //AuthServer address

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

            HttpEntity<Object> httpEntity = new HttpEntity<>((Object) request.getBioSign(), headers);
            ResponseEntity<AuthResponseDto> authResult = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, AuthResponseDto.class);

            AuthResponseDto result = authResult.getBody();

            log.info("## result" + result);

            PersonInfo personInfo = personService.personInfo(result.getMessage());

            log.info("## user from reg service: " + personInfo);

            UserVerificationResponse userVerificationResponse = UserVerificationResponse.builder()
                    .name(String.format("%s %s", personInfo.getFirstName(), personInfo.getLastName()))
                    .index(personInfo.getIndex())
                    .photo("photo")
                    .success(true)
                    .build();

            EntryPlace entryPlace = entryPlaceRepository
                    .findById(request.getEntryPlaceId()).orElseThrow();

            UniversityEntryLog universityEntryLog = UniversityEntryLog.builder()
                    .index(personInfo.getIndex())
                    .state(EntryState.IN)
                    .timestamp(LocalDateTime.now())
                    .entryPlace(entryPlace)
                    .approverEmail(subject)
                    .build();

            try {
                entryLogRepository.save(universityEntryLog);
            } catch (Exception e) {
                throw new DataSavingException("Failed to save entry log");
            }

            return userVerificationResponse;

        } catch (Exception e) {
            throw new UserNotFoundException("Failed to authenticate user");
        }


    }

}
