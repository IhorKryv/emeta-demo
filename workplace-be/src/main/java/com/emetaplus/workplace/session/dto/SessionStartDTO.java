package com.emetaplus.workplace.session.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SessionStartDTO {
    private UUID id;
    private String url;
}
