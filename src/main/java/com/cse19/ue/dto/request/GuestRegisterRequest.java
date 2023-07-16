package com.cse19.ue.dto.request;

import com.cse19.ue.model.Gender;
import com.cse19.ue.model.Title;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuestRegisterRequest {
    private Title title;
    private String name;
    private String phoneNumber;
    private String NIC;
    private Gender gender;
    private String inviterIndex;
    private Object bioSign;
    private Long approverId;

//    public Gender getGender() {
//        if(this.gender.equals(Gender.MALE.toString()))
//            return Gender.MALE;
//        else if(this.gender.equals(Gender.FEMALE.toString()))
//            return Gender.FEMALE;
//        return Gender.MALE;
//    }

    @Override
    public String toString() {
        return "GuestRegisterRequest{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", NIC='" + NIC + '\'' +
                ", gender='" + gender + '\'' +
                ", inviterIndex='" + inviterIndex + '\'' +
                ", bioSign=" + bioSign +
                ", approverId='" + approverId + '\'' +
                '}';
    }
}
