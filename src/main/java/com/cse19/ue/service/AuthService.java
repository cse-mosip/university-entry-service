package com.cse19.ue.service;

import com.cse19.ue.dto.AuthVerify;
import com.cse19.ue.dto.Token;
import com.cse19.ue.dto.request.LoginRequest;
import com.cse19.ue.dto.request.UserRegisterRequest;
import com.cse19.ue.dto.response.AuthResponse;
import com.cse19.ue.model.Role;
import com.cse19.ue.repository.UserRepository;
import lombok.AllArgsConstructor;

import com.cse19.ue.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${services.registration.url}")
    private String REGISTRATION_SERVICE;
    private final WebClient webClient = WebClient.builder().build();


    @Deprecated
    public String register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new IllegalArgumentException("Email already exists");

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(request.getRole())
                .build();

        userRepository.save(user);
        return "User registered successfully";
    }

    public AuthResponse login(LoginRequest request) {
        String apiEndpoint = REGISTRATION_SERVICE + "/api/public/verify";


        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
        bodyValues.add("email", request.getEmail());
        bodyValues.add("password", request.getPassword());


        AuthVerify verifyResponse = webClient.post()
                .uri(apiEndpoint)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(bodyValues))
                .retrieve()
                .bodyToMono(AuthVerify.class)
                .block();
//        log.info("{}", "verify response ok ");
        log.info("{}", verifyResponse);

        if (!verifyResponse.isValid())
            throw new IllegalArgumentException("Invalid credentials");

        Role authUserRole = null;
        try {
            authUserRole = Role.valueOf(verifyResponse.getRole().toUpperCase());
        } catch (Exception e) {
            log.info("{}", "invalid role type");
            throw new IllegalArgumentException("Unauthorized");
//            403
        }

        String scope = "read";
        if (authUserRole.name().equals(Role.ADMIN.name()))
            scope = "read write";

        Map<String, Object> extraClaims = Map.of(
                "role", authUserRole.name(),
                "scope", scope
        );

        Token accessToken = jwtService.generateJwtToken(extraClaims, verifyResponse.getEmail());
        return AuthResponse.builder()
                .accessToken(accessToken.getToken())
                .expiresIn(accessToken.getExpiresIn())
                .tokenType(accessToken.getTokenType())
                .refreshToken("refresh-token")
                .scope(scope)
                .build();
    }
}
