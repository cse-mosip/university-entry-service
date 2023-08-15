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
    private String index;

    public Decoded(Date expiryTime, Date issuedTime, String email, Role role) {
        this.expiryTime = expiryTime;
        this.issuedTime = issuedTime;
        this.email = email;
        this.role = role;
    }

}
