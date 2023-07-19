package com.cse19.ue.repository;

import com.cse19.ue.model.EntryPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntryPlaceRepository extends JpaRepository<EntryPlace, Integer> {


    Optional<Object> findByName(String name);
}
