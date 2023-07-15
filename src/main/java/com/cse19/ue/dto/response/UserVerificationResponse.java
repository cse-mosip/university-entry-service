package com.cse19.ue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserVerificationResponse {
    private String studentName;
    private String index;
    private String verification;
}
