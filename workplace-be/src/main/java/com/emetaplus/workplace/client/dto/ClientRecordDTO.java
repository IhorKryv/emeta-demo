package com.emetaplus.workplace.client.dto;

import com.emetaplus.workplace.client.model.ClientFile;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ClientRecordDTO {
    private UUID id;
    private UUID clientId;
    private String title;
    private String content;
    private LocalDateTime sessionDate;
    private LocalDateTime createdDate;

    private List<ClientFileDTO> files;

}
