package com.cse19.ue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cse19.ue.model.UniversityEntryLog;

public interface EntryLogRepository extends JpaRepository<UniversityEntryLog, Long> {

}
