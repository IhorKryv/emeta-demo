package com.emetaplus.workplace.userworkplace.model;

import lombok.Data;

@Data
public class UserWorkplaceSettings {

    private WorkplaceStatus workplaceStatus;
    private Integer freeDecksCount;
    private Integer freeDecksUsed;
    private Integer freeBoardsCount;
    private Integer freeBoardsUsed;
    private boolean allowOwnDecks;
    private boolean allowPersonalPage;
}
