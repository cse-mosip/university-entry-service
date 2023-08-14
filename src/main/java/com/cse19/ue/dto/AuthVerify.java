package com.cse19.ue.dto;

import com.cse19.ue.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthVerify {
    private boolean valid;
    private String email;
    private String role;
    private String firstname;
    private String lastname;
}
