package com.cse19.ue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoRequest {
    private List<String> indices;
    private List<String> fields;
}
