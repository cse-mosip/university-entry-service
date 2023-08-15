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
    private String name;
    private String index;
    private String photo;
    private boolean success;
}
