package com.cse19.ue.service;

import com.cse19.ue.dto.request.GuestRegisterRequest;
import com.cse19.ue.exception.Exceptions;
import com.cse19.ue.exception.ResponseStatusCodes;
import com.cse19.ue.model.Gender;
import com.cse19.ue.model.Guest;
import com.cse19.ue.model.User;
import com.cse19.ue.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UniversityEntryService {
    private GuestRepository guestRepository;

    @Autowired
    public UniversityEntryService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Object validateBSign(Object bSign) {
        if (bSign == null) {
            System.out.println("bsign is null");
            throw new Exceptions(ResponseStatusCodes.BIOMETRIC_SIGNATURE_CANNOT_BE_NULL);
        } else if (false) {
            System.out.println("bsign is false");

//            update condition with biometric response receiving method
            throw new Exceptions(ResponseStatusCodes.BIOMETRIC_SIGNATURE_IS_NOT_MATCHING);
        }
        return true;
    }


    public User retrieveUser(String approverId) {
        //logic for retriever user using id
        if (false)
            throw new Exceptions(ResponseStatusCodes.USER_NOT_EXIST_FOR_APPROVER_ID);
        return new User();
    }


    public Guest addGuest(GuestRegisterRequest request, String subject) {
        System.out.println(request.toString());
        validateBSign(request.getBioSign());

        Guest guest = new Guest();

        // get User from request.getApproverId()
//        guest.setApprover(new User(){{
//            setId(request.getApproverId());
//        }});
        guest.setApproverEmail(subject);
        guest.setInviterIndex(request.getInviterIndex());
        guest.setName(request.getName());
        guest.setNIC(request.getNIC());
//        String gender = request.getGender();
        guest.setGender(request.getGender());
        guest.setPhoneNumber(request.getPhoneNumber());
        guest.setTitle(request.getTitle());
        guest.setTimestamp();


        return guestRepository.save(guest);
    }
}
