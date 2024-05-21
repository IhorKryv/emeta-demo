package com.emetaplus.admin.deck.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ExportDeckDTO {
    private UUID adminId;
    private String name;
    private String description;
    private String cardBack;
    private String cardBackUrl;
    private String cardBackExtension;
    private int cardsCount;
}
