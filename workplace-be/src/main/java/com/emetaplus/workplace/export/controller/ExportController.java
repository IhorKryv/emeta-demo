package com.emetaplus.workplace.export.controller;

import com.emetaplus.workplace.export.dto.ExportBoardDTO;
import com.emetaplus.workplace.export.dto.ExportDeckDTO;
import com.emetaplus.workplace.export.service.ExportService;
import com.emetaplus.workplace.userdeck.dto.CardDTO;
import com.emetaplus.workplace.userdeck.dto.CardWithSizeDTO;
import com.emetaplus.workplace.userdeck.mapper.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class ExportController {

    private static final String GET_ALL_DECKS_FOR_EXPORT_PATH = "decks";
    private static final String GET_ALL_CARDS_FOR_EXPORTED_DECK_PATH = "decks/{deckId}/cards";

    private static final String GET_ALL_BOARDS_FOR_EXPORT_PATH = "boards";

    private final ExportService exportService;
    private final CardMapper cardMapper;

    @GetMapping(GET_ALL_DECKS_FOR_EXPORT_PATH)
    public List<ExportDeckDTO> getAllDecks() {
        return exportService.getDecksForExport();
    }

    @GetMapping(GET_ALL_BOARDS_FOR_EXPORT_PATH)
    public List<ExportBoardDTO> getAllBoards() {
        return exportService.getBoardsForExport();
    }

    @GetMapping(GET_ALL_CARDS_FOR_EXPORTED_DECK_PATH)
    public List<CardWithSizeDTO> getAllCardsForDeck(@PathVariable UUID deckId, @RequestParam(name = "adminDeck") boolean isAdminDeck) {
        return isAdminDeck
                ? exportService.getCardsForAdminDeck(deckId)
                : cardMapper.toCardWithSizeDTOList(exportService.getCardsForWorkplaceDeck(deckId));
    }
}
