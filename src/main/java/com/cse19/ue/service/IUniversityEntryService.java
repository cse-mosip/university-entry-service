package com.cse19.ue.service;

import com.cse19.ue.dto.request.GuestRegisterRequest;

public interface IUniversityEntryService {
    public String addGuest(GuestRegisterRequest request, String approverId, String bSign);
}
