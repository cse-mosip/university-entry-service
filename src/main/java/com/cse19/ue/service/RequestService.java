package com.cse19.ue.service;

import com.cse19.ue.dto.Decoded;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Service
public class RequestService {
    public Decoded getCurrentUserDetails() {
        Object authenticatedUserDetails = RequestContextHolder.currentRequestAttributes().getAttribute("auth",
                RequestAttributes.SCOPE_REQUEST);

        if (authenticatedUserDetails == null) {
            throw new RuntimeException("There is no user details for this request. " +
                    "The request probably does not require authentication.");
        }

        return (Decoded) authenticatedUserDetails;
    }
}
