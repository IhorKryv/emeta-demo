package com.emetaplus.workplace.builder.decks.dto;

import com.emetaplus.workplace.builder.decks.model.ProfileDeckItem;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProfileDeckDTO {

    private UUID id;
    private UUID profileId;
    private String title;
    private String info;
    private List<ProfileDeckItem> decks;
}
