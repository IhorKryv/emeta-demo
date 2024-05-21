package com.emetaplus.admin.deck.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CardDTO {
    private UUID id;
    private String url;
    private String extension;
}
