package com.cse19.ue.repository;

import com.cse19.ue.model.Guest;
import com.cse19.ue.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
