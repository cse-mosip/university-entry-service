package com.cse19.ue.service;

import com.cse19.ue.dto.Token;
import com.cse19.ue.dto.request.LoginRequest;
import com.cse19.ue.dto.request.UserRegisterRequest;
import com.cse19.ue.dto.response.AuthResponse;
import com.cse19.ue.model.Role;
import com.cse19.ue.repository.UserRepository;
import lombok.AllArgsConstructor;

import com.cse19.ue.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("Invalid password");

        String scope = "read";
        if (user.getRole().equals(Role.ADMIN))
            scope = "read write";


        Map<String, Object> extraClaims = Map.of(
                "role", user.getRole(),
                "scope", scope
        );


        Token accessToken = jwtService.generateJwtToken(extraClaims, user.getEmail());
        return AuthResponse.builder()
                .accessToken(accessToken.getToken())
                .expiresIn(accessToken.getExpiresIn())
                .tokenType(accessToken.getTokenType())
                .refreshToken("refresh-token")
                .scope(scope)
                .build();
    }
}
