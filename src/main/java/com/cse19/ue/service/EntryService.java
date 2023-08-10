package com.cse19.ue.service;


import com.cse19.ue.dto.request.SaveEntryRequest;
import com.cse19.ue.dto.response.EntranceRecordsResponse;
import com.cse19.ue.dto.response.UserVerificationResponse;
import com.cse19.ue.model.EntryState;
import com.cse19.ue.model.UniversityEntryLog;
import com.cse19.ue.repository.ExtendedEntryLogRepository;
import com.cse19.ue.repository.EntryLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class EntryService {
    private final EntryLogRepository entryLogRepository;
    private final ExtendedEntryLogRepository extendedEntryLogRepository;

    public EntranceRecordsResponse entranceRecords(
            String index,
            String faculty,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            int skip,
            int take
    ) {

        return extendedEntryLogRepository.filterEntranceLogs(index, faculty, fromDate, toDate, skip, take);
    }


    public UserVerificationResponse saveEntryLog(SaveEntryRequest request, String subject) {
        try {

            // TODO: verify from auth server

            UniversityEntryLog universityEntryLog = UniversityEntryLog.builder()
//                    .entryPlace()
//                    .approverEmail()
//                    .state(EntryState.IN)
                    .approverEmail(subject)
                    .build();
            entryLogRepository.save(universityEntryLog);

            return new UserVerificationResponse("kumara", "190999A", "verified");
        } catch (Exception e) {

            return new UserVerificationResponse("kumara", "190999A", "failed");

        }
    }

}
