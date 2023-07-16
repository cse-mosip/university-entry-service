package com.cse19.ue.service;

import com.cse19.ue.model.EntryPlace;
import com.cse19.ue.repository.EntryPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntryPlaceService {
    @Autowired
    private EntryPlaceRepository entryPlaceRepository;

    public EntryPlace registerUser(EntryPlace entryPlace) {
        if(entryPlaceRepository.findById(entryPlace.getId()).isPresent()){
            throw new RuntimeException("Entry Place already exists");
        }
        return entryPlaceRepository.save(entryPlace);
    }
}
