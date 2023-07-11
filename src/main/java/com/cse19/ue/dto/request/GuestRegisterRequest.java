package com.cse19.ue.dto.request;

import com.cse19.ue.model.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestRegisterRequest {
    private String title;
    private String name;
    private String phoneNumber;
    private String NIC;
    private String gender;
    private String inviterIndex;
}
