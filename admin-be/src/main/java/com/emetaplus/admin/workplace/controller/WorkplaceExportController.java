package com.emetaplus.admin.workplace.controller;

import com.emetaplus.admin.workplace.dto.WorkplaceExportDTO;
import com.emetaplus.admin.workplace.dto.WorkplaceExportSettingsDTO;
import com.emetaplus.admin.workplace.model.WorkplaceStatus;
import com.emetaplus.admin.workplace.service.WorkplaceExportService;
import com.emetaplus.admin.workplace.service.WorkplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/export/workplace")
@RequiredArgsConstructor
public class WorkplaceExportController {

    private static final String CREATE_WORKPLACE_PATH = "create";
    private static final String GET_WORKPLACE_SETTINGS_PATH = "{workplaceId}/settings";
    private static final String INCREASE_USED_DECKS_COUNT_PATH = "{workplaceId}/decks/increase";
    private static final String INCREASE_USED_BOARDS_COUNT_PATH = "{workplaceId}/boards/increase";

    private static final String GET_WORKPLACE_STATUS_PATH = "{workplaceId}/status";

    private final WorkplaceExportService workplaceExportService;

    @PostMapping(CREATE_WORKPLACE_PATH)
    public void createWorkplaceFromWorkplaceService(@RequestBody WorkplaceExportDTO dto) {
        workplaceExportService.createWorkplace(dto);
    }

    @GetMapping(GET_WORKPLACE_SETTINGS_PATH)
    public WorkplaceExportSettingsDTO getWorkplaceSettings(@PathVariable UUID workplaceId) {
        return workplaceExportService.getWorkplaceSettings(workplaceId);
    }

    @GetMapping(GET_WORKPLACE_STATUS_PATH)
    public WorkplaceStatus getWorkplaceStatus(@PathVariable UUID workplaceId) {
        return workplaceExportService.getWorkplaceStatus(workplaceId);
    }

    @GetMapping(INCREASE_USED_DECKS_COUNT_PATH)
    public void increaseUsedDecksCount(@PathVariable UUID workplaceId) {
        workplaceExportService.increaseUsedDecksCount(workplaceId);
    }

    @GetMapping(INCREASE_USED_BOARDS_COUNT_PATH)
    public void increaseUsedBoardsCount(@PathVariable UUID workplaceId) {
        workplaceExportService.increaseUsedBoardsCount(workplaceId);
    }
}
