package com.cse19.ue.config;


import com.cse19.ue.model.Role;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationFilter authenticationFilter;

//    @Bean
//    public AuthenticationFilter authenticationTokenFilter() {
//        return new AuthenticationFilter();
//    }

    @Bean
//    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/api/v1/auth/**", "/api/v1/health-check/**").permitAll();
                            auth.requestMatchers("/api/v1/admin/**", "/api/v1/gates/register").hasAuthority(Role.ADMIN.name());
                            auth.requestMatchers("/api/v1/entry/**").hasAuthority(Role.SECURITY.name());
                            auth.anyRequest().authenticated();
                        }
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

//        ;
//                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, AnonymousAuthenticationFilter.class);
//                csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/v1/auth/**", "/api/v1/home**").permitAll()
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/v1/admin/**").hasAuthority(ERole.ADMIN.name())
//                .requestMatchers("/api/v1/user/**").hasAuthority(ERole.USER.name())
//                .requestMatchers("/api/v1/multi/**").hasAnyAuthority(
//                        ERole.MODERATOR.name(),
//                        ERole.ADMIN.name(),
//                        ERole.SUPER_ADMIN.name()
//                )
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
