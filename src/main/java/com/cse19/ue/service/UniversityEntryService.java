package com.cse19.ue.service;

import com.cse19.ue.dto.request.GuestRegisterRequest;
import com.cse19.ue.model.Guest;
import com.cse19.ue.model.User;
import com.cse19.ue.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityEntryService implements IUniversityEntryService {
    private GuestRepository guestRepository;
    @Autowired
    public UniversityEntryService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }


    @Override
    public String addGuest(GuestRegisterRequest request,String approverId,String bSign){
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
