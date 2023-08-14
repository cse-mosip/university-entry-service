package com.cse19.ue.config;

import com.cse19.ue.dto.Decoded;
import com.cse19.ue.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.NonNull;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    //    @Order(1)
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        log.warn("AuthenticationFilter.doFilterInternal()");

        final String authHeader = request.getHeader("Authorization");
        final String token;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);


        /* FIXME: oauth2 flow can be change here
         * for now this is just token decode and validation process withing the service
         * when different flow applied remove the jwt service and implement the flow here
         * */

        Decoded decoded;

        try {
            decoded = jwtService.decodeJwtToken(token);
        } catch (Exception e) {
            log.error("exception: " + e.getMessage());

            filterChain.doFilter(request, response);
            return;
        }


        log.warn("decoded: " + decoded.toString());

        if (decoded == null) {
            log.warn("token not found");
            filterChain.doFilter(request, response);
            return;
        }

//        UserDetails userDetails = new UserDetails(Long.parseLong(decoded.getUsername()),
//                Integer.parseInt(decoded.getUserType()));

        log.warn("decoded: " + decoded.toString());

        Collection<? extends GrantedAuthority> roles = List.of(new SimpleGrantedAuthority(decoded.getRole().name()));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                decoded.getEmail(),
                null,
                roles
        );

        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

//        FiXME: use if needed in future
//        extraRequestContextHolder(decoded);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }


    private void extraRequestContextHolder(Decoded decoded) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        requestAttributes.setAttribute("auth", decoded, RequestAttributes.SCOPE_REQUEST);
    }
}
