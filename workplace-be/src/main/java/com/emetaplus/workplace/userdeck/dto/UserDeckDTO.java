package com.emetaplus.workplace.userdeck.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDeckDTO {

    private UUID id;
    private UUID workplaceId;
    private String name;
    private String description;
    private String cardBack;
    private String cardBackUrl;
    private int cardsCount;
    private boolean available;
}
