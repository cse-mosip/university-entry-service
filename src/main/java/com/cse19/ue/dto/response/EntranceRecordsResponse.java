package com.cse19.ue.dto.response;

import com.cse19.ue.dto.EntryRecord;
import com.cse19.ue.model.UniversityEntryLog;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntranceRecordsResponse {
    private int totalCount;
    private int skip;
    private int take;
    private List<EntryRecord> records;
}
