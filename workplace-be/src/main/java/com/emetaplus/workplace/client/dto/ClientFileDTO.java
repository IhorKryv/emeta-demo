package com.emetaplus.workplace.client.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientFileDTO {
    private UUID id;
    private UUID clientRecordId;
    private String name;
    private String url;
}
