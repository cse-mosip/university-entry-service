package com.cse19.ue.service;

import com.cse19.ue.dto.AuthResponseDto;
import com.cse19.ue.dto.request.GuestRegisterRequest;
import com.cse19.ue.exception.Exceptions;
import com.cse19.ue.exception.ResponseStatusCodes;
import com.cse19.ue.exception.UserNotFoundException;
import com.cse19.ue.model.Gender;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import com.cse19.ue.model.Guest;
import com.cse19.ue.model.User;
import com.cse19.ue.repository.GuestRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;


@Service
@Slf4j
public class UniversityEntryService {
    private GuestRepository guestRepository;

    @Value("${services.authentication.url}")
    private String AUTHENTICATION_URL;

    @Autowired
    public UniversityEntryService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Object validateBSign(Object bSign) throws UserNotFoundException {
        if (bSign == null) {
            log.error("bsign is null");
            throw new Exceptions(ResponseStatusCodes.BIOMETRIC_SIGNATURE_CANNOT_BE_NULL);
        }
        RestTemplate restTemplate = new RestTemplate();
        String uri = AUTHENTICATION_URL + "/upload"; //AuthServer address

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<Object> httpEntity = new HttpEntity<>(bSign, headers);
            ResponseEntity<AuthResponseDto> authResult = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, AuthResponseDto.class);
            AuthResponseDto result = authResult.getBody();

            log.info("bio sign authenticated: " + result);
        } catch(Exception e) {
            throw new UserNotFoundException("Invalid user");
        }
        return true;
    }


    public User retrieveUser(String approverId) {
        //logic for retriever user using id
        if (false)
            throw new Exceptions(ResponseStatusCodes.USER_NOT_EXIST_FOR_APPROVER_ID);
        return new User();
    }


    public Guest addGuest(GuestRegisterRequest request, String subject) throws UserNotFoundException {

        validateBSign(request.getBioSign());
        Guest guest = Guest.builder()
                .title(request.getTitle())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .NIC(request.getNIC())
                .gender(request.getGender())
                .timestamp(LocalDateTime.now())
                .approverEmail(subject)
                .build();

        // get User from request.getApproverId()
//        guest.setApprover(new User(){{
//            setId(request.getApproverId());
//        }});


        return guestRepository.save(guest);
    }
}
