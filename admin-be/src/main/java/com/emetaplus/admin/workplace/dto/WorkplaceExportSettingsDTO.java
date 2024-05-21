package com.emetaplus.admin.workplace.dto;

import com.emetaplus.admin.workplace.model.WorkplaceStatus;
import lombok.Data;

@Data
public class WorkplaceExportSettingsDTO {
    private WorkplaceStatus workplaceStatus;
    private Integer freeDecksCount;
    private Integer freeDecksUsed;
    private Integer freeBoardsCount;
    private Integer freeBoardsUsed;
    private boolean allowOwnDecks;
    private boolean allowPersonalPage;
}
