package com.cse19.ue.service;

import com.cse19.ue.dto.AuthVerify;
import com.cse19.ue.dto.InfoRequest;
import com.cse19.ue.dto.response.PersonInfo;
import com.cse19.ue.exception.Exceptions;
import com.cse19.ue.exception.ResponseStatusCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final WebClient webClient = WebClient.builder().build();

    @Value("${services.registration.url}")
    private String REGISTRATION_SERVICE;

    public PersonInfo personInfo(String index) {

        String apiEndpoint = REGISTRATION_SERVICE + "/api/public/data";


        InfoRequest infoRequest = new InfoRequest();
        infoRequest.setIndices(Arrays.asList(index));
        infoRequest.setFields(Arrays.asList(
                "index",
                "email",
                "firstName",
                "lastName",
                "faculty",
                "department"
        ));
        ParameterizedTypeReference<List<PersonInfo>> responseType = new ParameterizedTypeReference<List<PersonInfo>>() {
        };


        List<PersonInfo> personInfoList = webClient.post()
                .uri(apiEndpoint)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(infoRequest))
                .retrieve()
                .bodyToMono(responseType)
                .block();

//        log.info("Person info response: {}", personInfoList);

        if (personInfoList == null || personInfoList.isEmpty())
            throw new IllegalArgumentException("Invalid index");

        return personInfoList.get(0);
    }
}
