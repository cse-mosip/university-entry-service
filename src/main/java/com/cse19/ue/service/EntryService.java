package com.cse19.ue.service;


import com.cse19.ue.dto.AuthResponseDto;
import com.cse19.ue.dto.request.SaveEntryRequest;
import com.cse19.ue.dto.response.EntranceRecordsResponse;
import com.cse19.ue.dto.response.PersonInfo;
import com.cse19.ue.dto.response.UserVerificationResponse;
import com.cse19.ue.exception.ErrorHandler;
import com.cse19.ue.exception.Exceptions;
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

    @Value("${services.authentication.url}")
    private String AUTHENTICATION_URL;
    private String NO_MATCH_FOUND = "no match found";

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


    public UserVerificationResponse saveEntryLog(SaveEntryRequest request, String subject) throws UserNotFoundException {

        RestTemplate restTemplate = new RestTemplate();
        String uri = AUTHENTICATION_URL + "/reg/rcapture"; //AuthServer address

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        HttpEntity<Object> httpEntity = new HttpEntity<>(request.getBioSign(), headers);
        ResponseEntity<AuthResponseDto> authResult = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, AuthResponseDto.class);

        AuthResponseDto result = authResult.getBody();

        if (result.getMessage().equals(NO_MATCH_FOUND))
            throw new UserNotFoundException("Invalid user");

        PersonInfo personInfo = personService.personInfo(result.getMessage());

        UserVerificationResponse userVerificationResponse = UserVerificationResponse.builder()
                .name(String.format("%s %s", personInfo.getFirstName(), personInfo.getLastName()))
                .index(personInfo.getIndex())
                .photo("photo")
                .build();

        EntryPlace entryPlace = entryPlaceRepository
                .findById(request.getEntryPlaceId()).orElseThrow();

        UniversityEntryLog universityEntryLog = UniversityEntryLog.builder()
                .state(EntryState.IN)
                .timestamp(LocalDateTime.now())
                .entryPlace(entryPlace)
                .approverEmail(subject)
                .build();

        entryLogRepository.save(universityEntryLog);

        return userVerificationResponse;

    }

}
