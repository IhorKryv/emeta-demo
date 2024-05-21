package com.emetaplus.workplace.userdeck.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class AdminDeckDTO {

    private UUID id;
    private UUID adminId;
    private UUID workplaceId;
    private String name;
    private String customName;
    private String description;
    private String customDescription;
    private String cardBack;
    private String cardBackUrl;
    private String cardBackExtension;
    private int cardsCount;
    private boolean deckInCollection;
}
