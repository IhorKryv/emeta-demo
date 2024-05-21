package com.emetaplus.workplace.session.dto;

import com.emetaplus.workplace.client.dto.ClientDTO;
import com.emetaplus.workplace.session.model.SessionStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class SessionCalendarItemDTO {
    private UUID id;
    private String name;
    private String startTime;
    private String endTime;
    private LocalDate date;
    private Long sessionDuration;
    private SessionStatus sessionStatus;
    private String url;
    private List<ClientDTO> clients;
}
