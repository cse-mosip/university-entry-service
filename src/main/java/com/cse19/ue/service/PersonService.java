package com.cse19.ue.service;

import com.cse19.ue.dto.response.PersonInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final WebClient webClient = WebClient.builder().build();

    @Value("${services.registration.url}")
    private String REGISTRATION_SERVICE;

    public PersonInfo personInfo(String index, String token) {

        String apiEndpoint = String.format("{}/api/student/{}", REGISTRATION_SERVICE, index);

//        PersonInfo personInfoResponse = webClient.get()
//                .uri(apiEndpoint)
//                .header("Content-Type", "application/json")
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//                .retrieve().bodyToMono(PersonInfo.class).block();

        return PersonInfo.builder()
                .firstname("firstname")
                .lastname("lastname")
                .index(index)
                .level(1)
                .faculty("faculty")
                .build();
    }
}
