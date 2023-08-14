package com.cse19.ue.dto;

import com.cse19.ue.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Decoded {
    private Date expiryTime;
    private Date issuedTime;
    private String email;
    private Role role;
}
