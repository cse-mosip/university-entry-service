package com.cse19.ue.dto;

import com.cse19.ue.model.EntryState;
import com.cse19.ue.model.UniversityEntryLog;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntryRecord {
    private Long id;
    private String index;
    private EntryState state;
    private LocalDateTime timestamp;
    private String gateName;

    public EntryRecord(UniversityEntryLog log) {
        this.index = log.getIndex();
        this.state = log.getState();
        this.timestamp = log.getTimestamp();
        this.gateName = log.getEntryPlace().getName();
    }
}
