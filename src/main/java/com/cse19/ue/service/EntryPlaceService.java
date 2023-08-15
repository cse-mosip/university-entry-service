package com.cse19.ue.service;

import com.cse19.ue.dto.EntryPlaceDto;
import com.cse19.ue.dto.request.EntryPlaceRegisterRequest;
import com.cse19.ue.dto.response.GetGatesResponse;
import com.cse19.ue.model.EntryPlace;
import com.cse19.ue.repository.EntryPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntryPlaceService {
    @Autowired
    private EntryPlaceRepository entryPlaceRepository;

    public EntryPlace gateRegister(EntryPlaceRegisterRequest request) {
//        if (entryPlaceRepository.findById(entryPlace.getId()).isPresent()) {
//            throw new RuntimeException("Entry Place already exists");
//        }
        if (entryPlaceRepository.findByName(request.getName()).isPresent()) {
            throw new RuntimeException("Entry Place already exists");
        }
        EntryPlace entryPlace = EntryPlace.builder()
                .name(request.getName())
                .location(request.getLocation())
                .build();

        return entryPlaceRepository.save(entryPlace);
    }

    public GetGatesResponse getAllEntryPlaces() {

        List<EntryPlace> entryPlaces = entryPlaceRepository.findAll();
        List<EntryPlaceDto> entryPlaceDtoLists = entryPlaces.stream()
                .map(this::mapToEntryPlaceDTO)
                .collect(Collectors.toList());

        return GetGatesResponse.builder()
                .gates(entryPlaceDtoLists)
                .build();
    }

    private EntryPlaceDto mapToEntryPlaceDTO(EntryPlace entryPlace) {
        return new EntryPlaceDto(entryPlace.getId(), entryPlace.getName(), entryPlace.getLocation());
    }
}
