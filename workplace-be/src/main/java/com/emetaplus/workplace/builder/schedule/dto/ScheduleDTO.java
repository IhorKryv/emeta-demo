package com.emetaplus.workplace.builder.schedule.dto;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Data
public class ScheduleDTO {
    private UUID id;
    private UUID profileId;
    private String title;
    private String shortText;
    private Map<String, String> items = new TreeMap<>();
}
