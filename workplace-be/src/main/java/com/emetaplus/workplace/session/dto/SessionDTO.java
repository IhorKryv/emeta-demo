package com.emetaplus.workplace.session.dto;

import com.emetaplus.workplace.client.dto.ClientDTO;
import com.emetaplus.workplace.session.model.SessionStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class SessionDTO {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean inProgress;
    private Long sessionDuration;
    private LocalDateTime expectedEndTime;
    private String hostFullName;
    private SessionStatus sessionStatus;
    private UUID workplaceId;
    private String url;
    private Set<ClientDTO> clients;
    private boolean instant;
}
