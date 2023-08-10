package com.cse19.ue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String token;
    private int expiresIn;
    private String tokenType;
    private String grantType;
    private String scope;
}
