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
        log.info("bsign not null");
        RestTemplate restTemplate = new RestTemplate();
        String uri = AUTHENTICATION_URL + "/upload"; //AuthServer address

        try {

            HttpHeaders headers = new HttpHeaders();
            log.info("lign 52");

            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            log.info("lign 53");

            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            log.info("lign 54");

            HttpEntity<Object> httpEntity = new HttpEntity<>(bSign, headers);
            log.info("lign 55");

            ResponseEntity<AuthResponseDto> authResult = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, AuthResponseDto.class);
            log.info("lign 56");

            AuthResponseDto result = authResult.getBody();
            log.info("lign 57");

        } catch (Exception e) {
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
//        log.info("request.toString(): " + request.toString());

        validateBSign(request.getBioSign());
        log.info("after validating bio sign");
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

        log.info("before save guest");

        return guestRepository.save(guest);
    }
}
