package com.emetaplus.workplace.export.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ExportDeckDTO {
    private UUID id;
    private UUID workplaceId;
    private String name;
    private String description;
    private String cardBackUrl;
    private Integer cardsCount;
    private boolean adminDeck;
}
