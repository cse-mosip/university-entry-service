package com.cse19.ue.service;


import com.cse19.ue.dto.response.EntranceRecordsResponse;
import com.cse19.ue.repository.EntranceLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class EntryServices {
    private final EntranceLogRepository entranceLogRepository;

    public EntranceRecordsResponse entranceRecords(
            String index,
            String faculty,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            int skip,
            int take
    ) {

        return entranceLogRepository.filterEntranceLogs(index, faculty, fromDate, toDate, skip, take);
    }

}
