package com.cse19.ue.controller;

import com.cse19.ue.dto.request.GuestRegisterRequest;
import com.cse19.ue.model.Guest;
import com.cse19.ue.model.User;
import com.cse19.ue.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/entry")
public class UniversityEntryController {

    private GuestRepository guestRepository;

    @Autowired
    public UniversityEntryController(GuestRepository guestRepository){
        this.guestRepository = guestRepository;
    }

    @PostMapping(value = "/guest")
    public String handleForm(@ModelAttribute GuestRegisterRequest request, @RequestParam("approverId") String approverId, @RequestParam("bSign") String bSign){
        // get inviter details using bSign
        // return error if bSign not existing

        Guest guest = new Guest();

        guest.setInviterIndex("190999H");

        guest.setName(request.getName());
        guest.setNIC(request.getNIC());
        guest.setGender(request.getGender());
        guest.setPhoneNumber(request.getPhoneNumber());
        guest.setTimestamp();


        // get User from approverId
        guest.setApprover(new User());

        guestRepository.save(guest);
        return "guest record added successfully";

    }
}
