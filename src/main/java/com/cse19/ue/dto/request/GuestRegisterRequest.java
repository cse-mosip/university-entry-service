package com.cse19.ue.dto.request;

import com.cse19.ue.model.Gender;
import com.cse19.ue.model.Title;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GuestRegisterRequest {
    private Title title;
    private String name;
    private String phoneNumber;
    private String NIC;
    private Gender gender;
    private Object bioSign;
    private Long approverId;

}
